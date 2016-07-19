package Tank_war_6_0;
import java.io.*;
import java.util.Vector;

import javax.sound.sampled.*;
//������������
class AePlayWave extends Thread {

	private String filename;
	public AePlayWave(String wavfile) {
		filename = wavfile;

	}

	public void run() {

		File soundFile = new File(filename);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		auline.start();
		int nBytesRead = 0;
		//���ǻ���
		byte[] abData = new byte[512];

		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}

	
}

//��¼���� ����Ŷ�ȡ���ĵ���̹�˵����꼰����
class Nodes{
	int x;
	int y;
	int direct;
	public Nodes(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}
//��¼�࣬ͬʱҲ���Ա��������ص�����
class Record{
	//��¼ÿ�ص���̹�˵�����
	private static int enemeynums=20;
	//��¼ÿ���Լ�̹�˵�����
	private static int heronums=3;
	//��¼����ܳɼ�
	private static int allres=0;
	private static FileWriter fw= null;
	private static BufferedWriter bw =null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	private  Vector<Enemy> emy = new Vector<Enemy>();
	static Vector<Nodes>  nodes=new Vector<Nodes>();
	//�õ����˵��������귽��
	public  Vector<Nodes> getenemyInfo(){
		try {
			fr= new FileReader("G:/java/tankres.txt");
			br= new BufferedReader(fr);
			String n="";
			n=br.readLine();//�������ʱ���������մ��ı��ж�ȡ�ĵ�һ������
			allres=Integer.parseInt(n);
			while((n=br.readLine())!=null){
					String []xyd=n.split(" ");
					Nodes node= new Nodes(Integer.parseInt(xyd[0])
						,Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
						nodes.add(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return nodes;
	}
	//������ٵ��˵������͵��˵����꼰����
	public   void enemyInfosave(Vector<Enemy> emy){
		this.emy=emy;
		try {
			fw = new FileWriter("G:/java/tankres.txt");
			bw= new BufferedWriter(fw);
			bw.write(allres+"\r\n");
			//���浱ǰ���˵����꼰����
			for(int i=0;i<emy.size();i++){
				Enemy em =emy.get(i);//ȡ����һ��̹��
				if(em.islive){//������ž�ȡ
				String record =em.x+" "+em.y+" "+em.direct;
				bw.write(record+"\r\n");
				   }
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	//���ļ��ж�ȡ��һ���̹�˵�����
	public static void getRecord(){
		try {
			fr= new FileReader("G:/java/tankres.txt");
			br= new BufferedReader(fr);
			String temp=br.readLine();//�������ʱ���������մ��ı��ж�ȡ�ĵ�һ������
			allres=Integer.parseInt(temp);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	//����һ��ٵ�̹���������浽�ļ���
	public static void resPreservation(){
		try {
			fw = new FileWriter("G:/java/tankres.txt");
			bw= new BufferedWriter(fw);
			bw.write(allres+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	public static int getEnemeynums() {
		return enemeynums;
	}
	public static void setEnemeynums(int enemeynums) {
		Record.enemeynums = enemeynums;
	}
	public static int getHeronums() {
		return heronums;
	}
	public static void setHeronums(int heronums) {
		Record.heronums = heronums;
	}
	public static int getAllres() {
		return allres;
	}
	public static void setAllres(int allres) {
		Record.allres = allres;
	}
	
	public static void addres(){
		allres++;
	}
	public static void reduceEnemynums(){//����һ������̹����Ŀ
		enemeynums--;
	}
	public static void reduceHeronums(){//����һ���Լ�̹����Ŀ
		heronums--;
	}
	
	
}
//����һ��̹����
class Tank{
	int x=0;//̹�˺�����
	int y=0;//̹��������
	int direct=1;//̹�˷���1�ϣ�2�£�3��4��.
	int speed=1;//̹�˵��ٶ�
	int color;//̹�˵���ɫ
	boolean islive=true;
	public  Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
//����һ���ҵ�̹����
class Hero extends Tank{
	Vector<Shot> vst= new Vector<Shot>();
	Shot s;
	public Hero(int x,int y){
		super(x,y);
	}
	public void moveUp(){
		if(y>0){
		y-=speed;
		}
	}
	public void moveDown(){
		if(y<270){
		y+=speed;
		}
	}
	public void moveLeft(){
		if(x>0){
		x-=speed;
		}
	}
	public void moveRight(){
		if(x<370){
		x+=speed;
		}
	}
	public void shotEnemy(){
		switch(this.direct){
		case 1:
			s= new Shot(x+10,y,1);
			vst.add(s);
			break;
		case 2:
			s= new Shot(x+10,y+30,2);
			vst.add(s);
			break;
		case 3:
			s=new Shot(x,y+10,3);
			vst.add(s);
		    break;
		case 4:
			s=new Shot(x+30,y+10,4);
			vst.add(s);
			break;
		}
		Thread t= new Thread(s);
		t.start();
	}
}
//���˵�̹��
class Enemy extends Tank implements Runnable{
	int move =1;
	Vector<Shot> vst1= new Vector<Shot>();
	Vector<Enemy> vst2= new Vector<Enemy>();//����һ�����������Է��ʵ�MyPanel�����Ե���̹��
	public Enemy(int x, int y) {
		super(x, y);
	}
	//�����������̹�˵�����
	public void getotherEnemyvector(Vector<Enemy> vv){
		this.vst2=vv;
	}
	//�ж��Ƿ����������˵�̹��
	public boolean istouchotherEnemy(){
		boolean b = false;
		switch(this.direct){
		case 1://�ҵķ�������
				//ȡ��̹��
				for(int i=0;i<vst2.size();i++){
					Enemy em = vst2.get(i);
					if(em!=this){ //�����Լ����Լ��Ƚ�
						if(em.direct==1||em.direct==2){ //������˵ķ������ϻ�������
							if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
							if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
						}
						if(em.direct==3||em.direct==4){//������˵ķ��������������
							if(this.x>=em.x&&this.x<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
								return true;
							}
							if(this.x+20>=em.x&&this.x+20<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
								return true;
							}
						}
					}
				}
			break;
		case 2://�ҵķ�������
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //�����Լ����Լ��Ƚ�
					if(em.direct==1||em.direct==2){ //������˵ķ������ϻ�������
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
						if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//������˵ķ��������������
						if(this.x>=em.x&&this.x<=em.x+30&&this.y+30>=em.y&&this.y+30<=em.y+20){
							return true;
						}
						if(this.x+20>=em.x&&this.x+20<=em.x+30&&this.y+30>=em.y&&this.y+30<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		case 3://�ҵķ�������
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //�����Լ����Լ��Ƚ�
					if(em.direct==1||em.direct==2){ //������˵ķ������ϻ�������
						if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//������˵ķ��������������
						if(this.x>=em.x&&this.x<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
							return true;
						}
						if(this.x>=em.x&&this.x<=em.x+30&&this.y+20>=em.y&&this.y+20<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		case 4://�ҵķ�������
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //�����Լ����Լ��Ƚ�
					if(em.direct==1||em.direct==2){ //������˵ķ������ϻ�������
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//������˵ķ��������������
						if(this.x+30>=em.x&&this.x+30<=em.x+30&&this.y>=em.y&&this.y<=em.y+20){
							return true;
						}
						if(this.x+30>=em.x&&this.x+30<=em.x+30&&this.y+20>=em.y&&this.y+20<=em.y+20){
							return true;
						}
					}
				}
			}
			break;
		}
		
		
		return b;
	}
 	public void run() {
 		
		while(true){
			switch(direct){
			case 1:
				for(int i=0;i<30;i++)  {
					if(y>0&&!this.istouchotherEnemy()){
				y-=speed;
					}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				}
				break;
			case 2:  
				for(int i=0;i<30;i++)  {
					if(y<270&&!this.istouchotherEnemy()){//̹�˳�30 ��Ҫ��ȥ
				y+=speed;
					}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}}
				break;
			case 3:	
				for(int i=0;i<30;i++)  {
				if(x>0&&!this.istouchotherEnemy()){
					x-=speed;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}}
				break;
			case 4: 
				for(int i=0;i<30;i++)  {
				if(x<370&&!this.istouchotherEnemy()){  //̹�˳�30 ��Ҫ��ȥ
					x+=speed;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}}
				break;
			}
			if(move==1){
			this.direct=(int)(Math.random()*4+1);
			}
			if(this.islive==false){
				break;
			}
			
			//�ж�̹���Ƿ���Ҫ����ӵ�
			if(move==1){
				if(islive){
					if(vst1.size()<3){
						Shot s=null;//û���ӵ�
						//���
						switch(direct){
						case 1:
							s= new Shot(x+10,y,1);
							vst1.add(s);
							break;
						case 2:
							s= new Shot(x+10,y+30,2);
							vst1.add(s);
							break;
						case 3:
							s=new Shot(x,y+10,3);
							vst1.add(s);
						    break;
						case 4:
							s=new Shot(x+30,y+10,4);
							vst1.add(s);
							break;
						}
						Thread tt=new Thread(s);
						tt.start();
					}
				}
			}
		  }
		}
      }
	


//�ӵ���

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;//�ӵ�����(̹ͬ�˷���)
	int speed=2;//�ӵ��ٶ�
	boolean islive=true;
	public Shot(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}
		switch(direct){
		case 1:
			y-=speed;
			break;
		case 2:
			y+=speed;
			break;
		case 3:
			x-=speed;
			break;
		case 4:
			x+=speed;
			break;
		}
	//	System.out.println("�ӵ�x="+x+"�ӵ�y="+y);
		if(x>401||x<-1||y>301||y<-1){//�ӵ������Ϊ1
			islive=false;
			break;
			
		}
	  }	
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
// ը��
class Boom {
	int x,y;
	int life=10;
	boolean islive= true;
	public Boom(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void lifeDown(){
		if(life>0){
			life--;
		}else{
			islive=false;
		}
	}
}