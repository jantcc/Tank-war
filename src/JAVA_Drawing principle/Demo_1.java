package JAVA_Drawing principle;
import java.awt.*;

import javax.swing.*;
public class Demo_1 extends JFrame{
	MyPanel1 mp1= null;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Demo_1 demo = new Demo_1();
	}
	public Demo_1(){
		mp1 = new MyPanel1();
		this.add(mp1);
		this.setSize(500,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class MyPanel1 extends JPanel{
	
	public void paint(Graphics g){
		super.paint(g);
/*		g.drawOval(40, 40, 100, 100);//��Բ
		g.drawLine(10, 40, 30, 40);//��ֱ��
		g.drawRect(10, 20, 30, 40);//�����α߿�
		Image im = Toolkit.getDefaultToolkit().
				getImage(Panel.class.getResource("/1.jpg"));
		g.drawImage(im, 0, 0, 300, 160, this);//��ͼƬ
     	g.drawString("����", 100, 100);  //����
		*/
//��һֻ����
		g.setColor(Color.green);
		g.fillOval(118,83,13,15);
		g.fillRect(121,88,8,13);
		g.fillOval(93,110,20,10);
		g.fillOval(138,110,20,10);
		g.fillOval(93,150,20,10);
		g.fillOval(138,150,20,10);
		g.setColor(Color.red);
		g.fillOval(100, 100, 50, 70);
		g.setColor(Color.black);
		g.fillOval(120, 88, 3, 3);
		g.fillOval(126, 88, 3, 3);
	}
} 