package divide;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class process {

    //BufferedImage pic=null;
    double[][][] rgb=new double[256][256][256];
    int[][][] section=new int[256][256][256];
    //int n=5;
    int size=0;
    int dcount=0;
    //int[] c;

    int[] colorDefine(int n){
        int[] c=new int[n];
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        for(int i=0;i<n;i++){
            Color m=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            c[i]=m.getRGB();
        }
        return c;
    }


   /* Color myWhite = new Color(55, 55, 255); // Color white
    int c0 = myWhite.getRGB();
    Color myc1 = new Color(0, 255, 0); // Color white
    int c1 = myc1.getRGB();
    Color myc2 = new Color(255, 0, 75); // Color white
    int c2 = myc2.getRGB();
    Color myc3 = new Color(00, 250, 251); // Color white
    int c3 = myc3.getRGB();
    Color myc4 = new Color(255, 155, 155); // Color white
    int c4 = myc4.getRGB();*/



    public void initialarray(){
        for(int i=0;i<256;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<256;k++){
                    rgb[i][j][k]=0;
                    section[i][j][k]=-1;
                }
            }
        }
    }

    public void divide(int n,BufferedImage pic){
        initialarray();
        //size=pic.getWidth()*pic.getWidth();
        for(int i=0;i<pic.getWidth();i++){
            for(int j=0;j<pic.getHeight();j++){
                Color piccolor=new Color(pic.getRGB(i,j));
                int r,g,b;
                r=piccolor.getRed();
                b=piccolor.getBlue();
                g=piccolor.getGreen();
                rgb[r][g][b]=1;
                size++;
            }
        }
        selected arr=null;
        arr=new selected();
        arr.randomselect(rgb,n);
        division[] d=new division[size*n];

        while(true){
            System.out.println("running");
            distance(rgb,d,arr,n);
            System.out.println("dist is counted");
            set(pic,d,n);
            System.out.println("rgb is set");
            if(countcentroid(d,n,size,arr)) break;
            System.out.println("a iteration completed");
        }

    }

    public int FindSmallest (double [] arr1) {
        int index = 0;
        double min = arr1[index];

        for (int i=1; i<arr1.length; i++) {

            if (arr1[i] < min) {
                min = arr1[i];
                index = i;
            }
        }
        return index;
    }


    public void distance(double[][][] rgb, division[] d,selected arr,int n){
        //int c=0;
        int sectionresult;
        double[] dist=new double[n];
        for(int i=0;i<256;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<256;k++){
                    if(rgb[i][j][k]==1){
                         for(int p=0;p<n;p++){
                             //System.out.println(p);
                             dist[p]=Math.sqrt((arr.x[p]-i)*(arr.x[p]-i)+(arr.y[p]-j)*(arr.y[p]-j)+(arr.z[p]-k)*(arr.z[p]-k));
                             //c++;
                         }
                         for(int p=0;p<n;p++){
                             sectionresult=FindSmallest(dist);
                             //d[dcount]= new division(section,i,j,k);
                             section[i][j][k]=sectionresult;
                             dcount++;
                         }
                    }

                }
            }
        }
    }

    public void set(BufferedImage pic, division[] d,int n){
        String output="output"+".jpg";
        BufferedImage mainpic=null;
        File f=new File("0497.jpg");
        try {
            mainpic= ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] c;
        c=colorDefine(n);

        //for(int k=0;k<dcount;k++){
            for(int i=0;i<pic.getWidth();i++){
                for(int j=0;j<pic.getHeight();j++){
                   // System.out.println(i+" "+j );
                    Color piccolor=new Color(pic.getRGB(i,j));
                    int r,g,b;
                    r=piccolor.getRed();
                    b=piccolor.getBlue();
                    g=piccolor.getGreen();
                   // if(r==d[k].a && g==d[k].b && b==d[k].c ){
                       /* if(section[r][g][b]==0){
                            mainpic.setRGB(i,j,c0);
                        }
                        else if(section[r][g][b]==1){
                            mainpic.setRGB(i,j,c1);
                        }
                        else if(section[r][g][b]==2){
                            mainpic.setRGB(i,j,c2);
                        }
                        if(section[r][g][b]==3){
                            mainpic.setRGB(i,j,c3);
                        }
                        if(section[r][g][b]==4){
                            mainpic.setRGB(i,j,c4);
                        }*/
                       mainpic.setRGB(i,j,c[section[r][g][b]]);
                    //}
                }
            }
        //}
        try {
            ImageIO.write(mainpic, "jpg", new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean countcentroid(division[] d,int n,int size,selected arr){
        double[] p=new double[n];
        double[] q=new double[n];
        double[] r=new double[n];

        double[] sumx=new double[n];
        double[] sumy=new double[n];
        double[] sumz=new double[n];

        double[] count=new double[n];
        for(int i=0;i<n;i++){
            sumx[i]=0;
            sumy[i]=0;
            sumz[i]=0;
            count[i]=0;
        }

        for(int i=0;i<256;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<256;k++) {
                    if(section[i][j][k]!=-1){
                       // System.out.println("hmm");
                        count[section[i][j][k]]++;
                        sumx[section[i][j][k]]=sumx[section[i][j][k]]+i;
                        sumy[section[i][j][k]]=sumy[section[i][j][k]]+j;
                        sumz[section[i][j][k]]=sumz[section[i][j][k]]+k;
                    }
                }
            }
        }
        for(int i=0;i<n;i++){
            p[i]=sumx[i]/count[i];
            q[i]=sumy[i]/count[i];
            r[i]=sumz[i]/count[i];
        }


        /*for(int i=0;i<size;i++){
            System.out.println(d[i].t+" "+i);
        }*/
        /*for(int i=0;i<5;i++){
            double count=0;
            double sumx=0,sumy=0,sumz=0;
            for(int j=0;i<50344;j++){
                if(d[j].t==i){
                    sumx=sumx+d[j].a;
                    sumy=sumy+d[j].b;
                    sumz=sumz+d[j].c;
                    count++;
                }
            }

            p[i]=sumx/count;
            q[i]=sumy/count;
            r[i]=sumz/count;

        }*/

        for(int i=0;i<n;i++){
            double distan=Math.sqrt((arr.x[i]-p[i])*(arr.x[i]-p[i])+(arr.y[i]-q[i])*(arr.y[i]-q[i])+(arr.z[i]-r[i])*(arr.z[i]-r[i]));
            System.out.println(distan);
            if(distan>.0000001){
                arr.x[i]=p[i];
                arr.y[i]=q[i];
                arr.z[i]=r[i];
                return false;
            }
        }
        return  true;

    }


}
