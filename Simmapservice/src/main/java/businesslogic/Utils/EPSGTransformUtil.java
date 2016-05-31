package businesslogic.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * Created by fke on 22.03.2016.
 */
public class EPSGTransformUtil {
    private CoordinateReferenceSystem sourceCrs;
    private CoordinateReferenceSystem targetCrs;

    public EPSGTransformUtil(String sourceCrs) throws FactoryException {
        this(sourceCrs,"WGS84"); //"EPSG:3857"
    }

    public EPSGTransformUtil(String sourceCrs, String targetCrs) throws FactoryException {
        this.sourceCrs = CRS.decode(sourceCrs);
        if(targetCrs.equals("WGS84")){
            this.targetCrs = DefaultGeographicCRS.WGS84;
        }
        else{
            this.targetCrs = CRS.decode(targetCrs);
        }

    }

    public Geometry transform(Coordinate coord) throws TransformException, FactoryException {
        MathTransform transform = CRS.findMathTransform(sourceCrs,targetCrs);

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        Point point = geometryFactory.createPoint(coord);
        Geometry transform1 = JTS.transform(point, transform);
        return  transform1;
    }
}
