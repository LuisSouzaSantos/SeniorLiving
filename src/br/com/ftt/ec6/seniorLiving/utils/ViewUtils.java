package br.com.ftt.ec6.seniorLiving.utils;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewUtils {
	
	public static ImageView createImageView(String id, String imagePath, double fitHeight, double fitWidth, boolean pickOnBounds, boolean preserveRatio, Cursor cursor) {
		ImageView imageView = new ImageView();
		Image image = ViewUtils.createIamge(imagePath);
		imageView.setImage(image);
		imageView.setId(id);
		imageView.setFitHeight(fitHeight);
		imageView.setFitWidth(fitWidth);
		imageView.setPickOnBounds(pickOnBounds);
		imageView.setPreserveRatio(preserveRatio);
		imageView.setCursor(cursor);
		return imageView;
	}
	
	public static Image createIamge(String imagePath) {
		Image image = new Image(imagePath);
		return image;
	}

}
