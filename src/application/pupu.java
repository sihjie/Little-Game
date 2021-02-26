package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class pupu extends Group{
	int type,ID;
	double positionX,positionY;
	ImageView pupu;
	ImageView pupu1;
	ImageView pupu2;
	ImageView pupu3;

	public pupu(int ID,Image pupu,int type,double positionX,double positionY ) {
		this.ID = ID;
		this.type = type;
		this.positionX=positionX;
		this.positionY=positionY;
		this.pupu= new ImageView(pupu);
		this.pupu.setTranslateX(positionX);
		this.pupu.setTranslateY(positionY);
	}

}
