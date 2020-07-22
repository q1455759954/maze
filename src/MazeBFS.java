import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * 生成迷宫用了BFS
 * 暴力破解用了DFS
 * 另一个Maze文件生成破解都是DFS
 */
public class MazeBFS extends JFrame {

    private boolean[][] map = new boolean[41][41];
    private boolean[][] canVisited = new boolean[41][41];
    private List<Point> pointList = new ArrayList<Point>();//记录路线
    List<String> list = new ArrayList<>();

    public MazeBFS() {
        super("迷宫");
        initFrame();
        initMap();
        initVisited();
        BFSBuildMaze(1,1);//生成迷宫

        initVisited();//再次初始化
        DFSWay(1,1);//寻路


    }

    private void DFSWay(int x,int y) {
        if (x==39&&y==39){
            JOptionPane.showMessageDialog(null,
                    "寻路结束!", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }else{
            if (judgeWay(x,y)){

                //向右
                if (x+1<=40) {
                    if (canVisited[x + 1][y]) {
                        Point point = new Point(x + 1, y);
                        pointList.add(point);
                        canVisited[x + 1][y] = false;
                        waitTime();
                        DFSWay(x + 1, y);

                    }
                }
                if (y+1<=40){
                    //向下
                    if (canVisited[x][y+1]){
                        Point point = new Point(x,y+1);
                        pointList.add(point);
                        canVisited[x][y+1]=false;
                        waitTime();
                        DFSWay(x,y+1);
                    }
                }
                if (x-1>=0){
                    //向左
                    if (canVisited[x-1][y]){
                        Point point = new Point(x-1,y);
                        pointList.add(point);
                        canVisited[x-1][y]=false;
                        waitTime();
                        DFSWay(x-1,y);
                    }
                }
                if (y-1>=0){
                    //向上
                    if (canVisited[x][y-1]){
                        Point point = new Point(x,y-1);
                        pointList.add(point);
                        canVisited[x][y-1]=false;
                        waitTime();
                        DFSWay(x,y-1);
                    }
                }

            }else {
                int m = pointList.get(pointList.size()-1).x;
                int n = pointList.get(pointList.size()-1).y;
                pointList.remove(pointList.size()-1);
                waitTime();
                DFSWay(m,n);
            }

        }//else


    }

    private boolean judgeWay(int x, int y) {
        //向右
        if (x+1<=40){
            if (canVisited[x+1][y]){
                return true;
            }
        }
        //向左
        if (x-1>=0){
            if (canVisited[x-1][y]){
                return true;
            }
        }
        //向下
        if (y+1<=40){
            if (canVisited[x][y+1]){
                return true;
            }
        }
        //向上
        if (y-1>=0){
            if (canVisited[x][y-1]){
                return true;
            }
        }
        return false;
    }

    //初始化canVisited
    private void initVisited() {
        for (int i=0;i<41;i++){
            for (int j=0;j<41;j++){
                if (!map[i][j]){
                    canVisited[i][j]=true;
                }
            }
        }
    }

    private void BFSBuildMaze(int x,int y) {
            String a = x+","+y;
            list.add(a);
            while (list.size()>0){
                int index = (int) (Math.random() * 1000) % list.size();
                a = list.get(index);
                list.remove(index);
                x = Integer.parseInt(a.split(",")[0]);
                y = Integer.parseInt(a.split(",")[1]);
                if (!judgeNextWall(x,y)){
                    continue;
                }

                //向左
                if (x-2>=0){
                    if (canVisited[x-2][y]){
                        map[x-1][y]=false;
                        canVisited[x-2][y]=false;
                        a = x-2+","+y;
                        list.add(a);
                    }
                }
                //向右
                if (x+2<=40){
                    if (canVisited[x+2][y]){
                        map[x+1][y]=false;
                        canVisited[x+2][y]=false;
                        a = x+2+","+y;
                        list.add(a);                    }
                }
                //向上
                if (y-2>=0){
                    if (canVisited[x][y-2]){
                        map[x][y-1]=false;
                        canVisited[x][y-2]=false;
                        a = x+","+(y-2);
                        list.add(a);
                    }
                }
                //向下
                if (y+2<=40){
                    if (canVisited[x][y+2]){
                        map[x][y+1]=false;
                        canVisited[x][y+2]=false;
                        a = x+","+(y+2);
                        list.add(a);                    }
                }
                waitTime();
            }

    }

    //等待，观看效果
    private void waitTime() {
        repaint();
        try {
            sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean judgeNextWall(int x,int y){
        //向右
        if (x+2<=40){
            if (canVisited[x+2][y]){
                return true;
            }
        }
        //向左
        if (x-2>=0){
            if (canVisited[x-2][y]){
                return true;
            }
        }
        //向下
        if (y+2<=40){
            if (canVisited[x][y+2]){
                return true;
            }
        }
        //向上
        if (y-2>=0){
            if (canVisited[x][y-2]){
                return true;
            }
        }
        return false;
    }


    private void initMap() {
        int m = 1;
        //初始化里面的数据
        for (int i = 1; i < 40; i++) {
            for (int j = (m % 2 + 1); j < 40; j += 2) {
                map[i][j] = true;
            }
            m++;
        }
        for (int i = 0; i < 41; i += 2) {
            for (int j = 0; j < 41; j++) {
                map[i][j] = true;
                map[j][i] = true;
            }
        }
    }

    private void initFrame() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drwaPaint(g);
            }
        };
        add(panel);
        setDefaultCloseOperation(3);
        setSize(900,900);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void drwaPaint(Graphics g) {

        //画迷宫
        for (int i=0;i<41;i++){
            for(int j=0;j<41;j++){
                if (map[i][j]){
                    g.setColor(Color.red);
                    g.fillRect(i*20,j*20,20,20);
                }
            }
        }
        //画路线
        for (Point point:pointList){
            g.setColor(Color.BLUE);
            g.fillOval(point.x*20,point.y*20,10,10);
        }
    }


    public static void main(String[] args){
        new MazeBFS();
    }

}