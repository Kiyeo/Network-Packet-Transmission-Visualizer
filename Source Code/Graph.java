import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel {
		private int WIDTH = 1000;
		private int HEIGHT = 325;
		private int graphWidth = 900;
		private int graphHeight = 250;
		private int graphXpos = 50;
		private int graphYpos = 25;
		private int tick = 5;
		private double scale;
		private double tickSpacing;
		int xAxis = 0;
		int yAxis = 0;
		JLabel yAxisLabel = new JLabel("Volume [bytes]");;
		JLabel xAxisLabel = new JLabel("Time [s]");
		
		boolean drawCord = false;
		double byteArrayMax;
		int tickNum = 100;
		
		public void redrawGraph() {
			WindowGUI.g.repaint();
			drawCord = true;
		}
		
		Graph() {
			setLayout(null);
			setBounds(0, 100, WIDTH, HEIGHT);
			setBackground(Color.white);
			yAxisLabel.setBounds(10, 0, WIDTH - 100, 15);
			add(yAxisLabel);
			
			xAxisLabel.setBounds(480, 305, 100, 15);
			add(xAxisLabel);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g.drawLine(graphXpos, graphYpos, graphXpos, graphYpos + graphHeight);
			g.drawLine(graphXpos, graphYpos + graphHeight, graphXpos + graphWidth, graphYpos + graphHeight);
			
			scale = graphWidth /  fileReadLine.timestampMax;
			tickSpacing = graphXpos * scale;
			int xlabel = 0;
			float labelXpos = 4;
			for(double i = 0; i <= graphWidth; i += tickSpacing) {
				g2.draw(new Line2D.Double(i  + graphXpos, graphYpos + graphHeight, i + graphXpos, graphYpos + graphHeight + tick));
				if(i > 100) {
					labelXpos = 10;
				} else if(i > 50) {
					labelXpos = 6;
				}
				g2.drawString(String.format("%d" , xlabel), (float) (graphXpos + i) - labelXpos, (float) (graphYpos + graphHeight + 20));
				xlabel += 50;
			}
			tickSpacing = 300;
			byteArrayMax = 0;
			if(WindowGUI.comboBox.isVisible()) {
				for(int i = 0; i < getGraphData.byteArray.length - 1; i++) {
					if(getGraphData.byteArray[i] > byteArrayMax) {
						byteArrayMax = getGraphData.byteArray[i];
					}
					
				}
				scale = (double) (graphHeight) / byteArrayMax;

				int n = (int) byteArrayMax;
				while (n >= 10) {
			        n /= 10;
				}
				if(byteArrayMax < 400000) {
					tickNum = 25;
					tickSpacing = (((byteArrayMax + byteArrayMax / n / 2) / (n + 1)) * scale) / 4;
				} else if(byteArrayMax < 1000000) {
					tickNum = 100;
					tickSpacing = ((byteArrayMax + byteArrayMax / n / 2) / (n + 1)) * scale;
				} else if(byteArrayMax > 1000000 & byteArrayMax < 2000000){
					tickNum = 250;
					tickSpacing = (((byteArrayMax + byteArrayMax / n / 2) / (n + 1)) * scale) / 4;
				} else if(byteArrayMax < 4000000){
					tickNum = 500;
					tickSpacing = (((byteArrayMax + byteArrayMax / n / 2) / (n + 1)) * scale) / 2;
				} 
			}
			
			int ylabel = 0;
			float labelYpos = 31;
			for(double i = 0; i <= graphHeight; i += tickSpacing) {
				g2.draw(new Line2D.Double(graphXpos - 5, graphYpos + graphHeight - i, graphXpos, graphYpos + graphHeight - i));
				if(i != 0) {
					labelYpos = 40;
					g2.drawString(String.format("%d" , ylabel) + "k", (float) (graphXpos - labelYpos), (float) (graphYpos + graphHeight - i + 5));
				} else {
				g2.drawString(String.format("%d" , ylabel), (float) (graphXpos - labelYpos), (float) (graphYpos + graphHeight - i + 5));
				}
				ylabel += tickNum;
			}

			if(drawCord == true) {
				for(int i = 0; i < getGraphData.slot - 1; i++) {
					double byteDataToPixel = getGraphData.intervalArray[i] * (graphWidth / fileReadLine.timestampMax);
					g2.setColor(Color.RED);
					Rectangle2D myRect = new Rectangle2D.Double(graphXpos + byteDataToPixel, graphYpos + graphHeight,  2, (getGraphData.byteArray[i]) * scale);
					//g2.draw(new Rectangle2D.Double(graphXpos + byteDataToPixel, graphYpos + graphHeight,  2, -(getGraphData.byteArray[i]) * scale));
					//g2.draw(new Line2D.Double(graphXpos + test, graphYpos + graphHeight,  graphXpos + test, graphYpos + graphHeight - (getGraphData.byteArray[i]) * scale));
					AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(180), graphXpos + byteDataToPixel, graphYpos + graphHeight);
					Shape rope = at.createTransformedShape(myRect);
					g2.draw(rope);
				}
			}
		}
	}