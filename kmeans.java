package divide;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class kmeans {

    public static void main(String[] args) {
        String image="0497.jpg";

        File mainImage=new File(image);

        try {
            BufferedImage mainpic= ImageIO.read(mainImage);
            process p=new process();
            p.divide(15,mainpic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
