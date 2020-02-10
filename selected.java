package divide;

public class selected {

    public selected( ){

    }

    double[] x;
    double[] y;
    double[] z;

    public void randomselect(double[][][] rgb,int n){
        x=new double[n];
        y=new double[n];
        z=new double[n];
        int t=0;
        for(int i=0;i<256;i++){
            for(int j=0;j<256;j++){
                for(int k=0;k<256;k++){
                    if(rgb[i][j][k]==1 && t<n){
                        //System.out.println(t);
                        x[t]=i;
                        y[t]=j;
                        z[t]=k;
                        t++;
                    }
                }
            }
        }
        System.out.println("t is "+t);
    }
}
