package Tank_war_6_0;
import java.io.*;
import java.util.Vector;

import javax.sound.sampled.*;
//播放声音的类
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
		//这是缓冲
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

//记录点类 （存放读取到的敌人坦克的坐标及方向）
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
//记录类，同时也可以保存玩家相关的设置
class Record{
	//记录每关敌人坦克的数量
	private static int enemeynums=20;
	//记录每关自己坦克的数量
	private static int heronums=3;
	//记录玩家总成绩
	private static int allres=0;
	private static FileWriter fw= null;
	private static BufferedWriter bw =null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	private  Vector<Enemy> emy = new Vector<Enemy>();
	static Vector<Nodes>  nodes=new Vector<Nodes>();
	//得到敌人的数量坐标方向
	public  Vector<Nodes> getenemyInfo(){
		try {
			fr= new FileReader("G:/java/tankres.txt");
			br= new BufferedReader(fr);
			String n="";
			n=br.readLine();//定义个临时变量来接收从文本中读取的第一行数据
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return nodes;
	}
	//保存击毁敌人的数量和敌人的坐标及方向
	public   void enemyInfosave(Vector<Enemy> emy){
		this.emy=emy;
		try {
			fw = new FileWriter("G:/java/tankres.txt");
			bw= new BufferedWriter(fw);
			bw.write(allres+"\r\n");
			//保存当前敌人的坐标及方向
			for(int i=0;i<emy.size();i++){
				Enemy em =emy.get(i);//取出第一个坦克
				if(em.islive){//如果活着就取
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
	//从文件中读取玩家击毁坦克的数量
	public static void getRecord(){
		try {
			fr= new FileReader("G:/java/tankres.txt");
			br= new BufferedReader(fr);
			String temp=br.readLine();//定义个临时变量来接收从文本中读取的第一行数据
			allres=Integer.parseInt(temp);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	//把玩家击毁的坦克数量保存到文件中
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
	public static void reduceEnemynums(){//减掉一个敌人坦克数目
		enemeynums--;
	}
	public static void reduceHeronums(){//减掉一个自己坦克数目
		heronums--;
	}
	
	
}
//定义一个坦克类
class Tank{
	int x=0;//坦克横坐标
	int y=0;//坦克纵坐标
	int direct=1;//坦克方向，1上，2下，3左，4右.
	int speed=1;//坦克的速度
	int color;//坦克的颜色
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
//定义一个我的坦克类
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
//敌人的坦克
class Enemy extends Tank implements Runnable{
	int move =1;
	Vector<Shot> vst1= new Vector<Shot>();
	Vector<Enemy> vst2= new Vector<Enemy>();//定义一个向量，可以访问到MyPanel上所以敌人坦克
	public Enemy(int x, int y) {
		super(x, y);
	}
	//获得其他敌人坦克的向量
	public void getotherEnemyvector(Vector<Enemy> vv){
		this.vst2=vv;
	}
	//判断是否与其他敌人的坦克
	public boolean istouchotherEnemy(){
		boolean b = false;
		switch(this.direct){
		case 1://我的方向向上
				//取出坦克
				for(int i=0;i<vst2.size();i++){
					Enemy em = vst2.get(i);
					if(em!=this){ //不能自己和自己比较
						if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
							if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
							if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
								return true;
							}
						}
						if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
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
		case 2://我的方向向下
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
						if(this.x+20>=em.x&&this.x+20<=em.x+20&&this.y+30>=em.y&&this.y+30<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
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
		case 3://我的方向向左
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x>=em.x&&this.x<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x>=em.x&&this.x<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
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
		case 4://我的方向向右
			for(int i=0;i<vst2.size();i++){
				Enemy em = vst2.get(i);
				if(em!=this){ //不能自己和自己比较
					if(em.direct==1||em.direct==2){ //如果敌人的方向向上或者向下
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y>=em.y&&this.y<=em.y+30){
							return true;
						}
						if(this.x+30>=em.x&&this.x+30<=em.x+20&&this.y+20>=em.y&&this.y+20<=em.y+30){
							return true;
						}
					}
					if(em.direct==3||em.direct==4){//如果敌人的方向向左或者向右
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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				}
				break;
			case 2:  
				for(int i=0;i<30;i++)  {
					if(y<270&&!this.istouchotherEnemy()){//坦克长30 ，要减去
				y+=speed;
					}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}}
				break;
			case 4: 
				for(int i=0;i<30;i++)  {
				if(x<370&&!this.istouchotherEnemy()){  //坦克长30 ，要减去
					x+=speed;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
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
			
			//判断坦克是否需要添加子弹
			if(move==1){
				if(islive){
					if(vst1.size()<3){
						Shot s=null;//没有子弹
						//添加
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
	


//子弹类

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;//子弹方向(同坦克方向)
	int speed=2;//子弹速度
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
	//	System.out.println("子弹x="+x+"子弹y="+y);
		if(x>401||x<-1||y>301||y<-1){//子弹的体积为1
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
// 炸弹
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