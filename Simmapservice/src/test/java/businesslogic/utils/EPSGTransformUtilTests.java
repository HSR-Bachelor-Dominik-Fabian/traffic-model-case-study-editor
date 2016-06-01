package businesslogic.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.junit.Test;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import static org.junit.Assert.*;

public class EPSGTransformUtilTests {

    private static final double DELTA = 1e-15;

    @Test
    public void testTransformEPSG3857_1() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:3857");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(8.983152841195215E-5, newCoordinate.x, DELTA);
        assertEquals(8.983152840993817E-5, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG3857_2() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:3857", "WGS84");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(8.983152841195215E-5, newCoordinate.x, DELTA);
        assertEquals(8.983152840993817E-5, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG21781_1() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:21781");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-0.16375999711764477, newCoordinate.x, DELTA);
        assertEquals(44.890315260682286, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG21781_2() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:21781", "WGS84");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-0.16375999711764477, newCoordinate.x, DELTA);
        assertEquals(44.890315260682286, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG26914_1() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:26914");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-103.48865429474007, newCoordinate.x, DELTA);
        assertEquals(9.019376924013694E-5, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG26914_2() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:26914", "WGS84");

        Coordinate coordinate = new Coordinate(10, 10);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-103.48865429474007, newCoordinate.x, DELTA);
        assertEquals(9.019376924013694E-5, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG32719_1() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:32719");

        Coordinate coordinate = new Coordinate(441867.78, 1116915.04);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-72.00000026147059, newCoordinate.x, DELTA);
        assertEquals(-80.00000003396194, newCoordinate.y, DELTA);
    }

    @Test
    public void testTransformEPSG32719_2() throws FactoryException, TransformException {
        EPSGTransformUtil transformUtil = new EPSGTransformUtil("EPSG:32719", "WGS84");

        Coordinate coordinate = new Coordinate(441867.78, 1116915.04);
        Geometry geometry = transformUtil.transform(coordinate);
        Coordinate newCoordinate = geometry.getCoordinate();
        assertEquals(-72.00000026147059, newCoordinate.x, DELTA);
        assertEquals(-80.00000003396194, newCoordinate.y, DELTA);
    }
}
