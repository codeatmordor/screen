/*
 * package com.gk.screenshot.service;
 * 
 * import javafx.application.Platform; import javafx.embed.swing.JFXPanel;
 * import javafx.embed.swing.SwingFXUtils; import javafx.scene.Scene; import
 * javafx.scene.SnapshotParameters; import javafx.scene.image.WritableImage;
 * import javafx.scene.web.WebEngine; import javafx.scene.web.WebView;
 * 
 * import java.io.ByteArrayOutputStream; import java.io.IOException; import
 * java.util.Arrays;
 * 
 * import javax.imageio.ImageIO; import javax.swing.*; import
 * javafx.scene.layout.Region;
 * 
 * public class WebMaib {
 * 
 * public static void main(String args[]) { final JFrame frame=new JFrame();
 * frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 * frame.setSize(620,440); final JFXPanel fxpanel=new JFXPanel();
 * frame.add(fxpanel);
 * 
 * Platform.runLater(new Runnable() {
 * 
 * @Override public void run() { WebEngine engine; WebView wv=new WebView();
 * engine=wv.getEngine(); Region r = new Region();
 * 
 * fxpanel.setScene(new Scene(wv));
 * engine.load("http://javatongue.blogspot.com"); WritableImage image =
 * r.snapshot(new SnapshotParameters(), null); ByteArrayOutputStream baos = new
 * ByteArrayOutputStream(); try { ImageIO.write(SwingFXUtils.fromFXImage(image,
 * null), "jpg", baos); } catch (IOException e) { // TODO Auto-generated catch
 * block e.printStackTrace(); } byte[] bytes = baos.toByteArray();
 * System.out.println("Screenshot saved as " + "abc" + ".png");
 * System.out.println(Arrays.toString(bytes)); Platform.exit();
 * //wv.getEngine().getDocument()!=null) } }); frame.setVisible(false); } }
 */