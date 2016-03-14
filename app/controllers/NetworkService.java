package controllers;

import org.json.JSONObject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import businessLogic.mercatorconvert.*;

import java.io.FileInputStream;

/**
 * Created by dohee on 10.03.2016.
 */
public class NetworkService extends Controller {
    public static Result getNetworks(int x, int y, int z){

        System.out.println("x:" + x + ", y:"+ y + ", z:" + z);
        BoundingBox coord = Converter.tile2boundingBox(x,y,z);
        System.out.println("north:" + coord.north + ", west:"+ coord.west + ", south:" + coord.south + ", east:" + coord.east);

        return ok();
    }
}
