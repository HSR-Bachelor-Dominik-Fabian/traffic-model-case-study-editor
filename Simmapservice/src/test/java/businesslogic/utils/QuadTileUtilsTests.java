package businesslogic.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuadTileUtilsTests {

    @Test
    public void testGetQuadTileKey_1() {
        String quadKey = QuadTileUtils.getQuadTileKey(10, 10, 10);

        assertEquals("0000003030", quadKey);
    }

    @Test
    public void testGetQuadTileKey_2() {
        String quadKey = QuadTileUtils.getQuadTileKey(268, 179, 9);

        assertEquals("120221122", quadKey);
    }

    @Test
    public void testGetQuadTileKey_3() {
        String quadKey = QuadTileUtils.getQuadTileKey(1074, 717, 11);

        assertEquals("12022112212", quadKey);
    }

    @Test
    public void testGetQuadKeyFromLatLng() {
        String quadKey = QuadTileUtils.getQuadTileKeyFromLatLong(10, 10);

        assertEquals("122221112203312001", quadKey);
    }

    @Test
    public void testMinCommonQuadTileKeyFromLatLong() {
        String firstQuadKey = QuadTileUtils.getQuadTileKeyFromLatLong(10, 10);
        String secondQuadKey = QuadTileUtils.getQuadTileKeyFromLatLong(11, 11);

        String expectedCommonQuadKey = "";
        int minLength = Math.min(firstQuadKey.length(), secondQuadKey.length());
        for (int i = 0; i < minLength; i++) {
            if (firstQuadKey.charAt(i) != secondQuadKey.charAt(i)) {
                expectedCommonQuadKey = firstQuadKey.substring(0, i);
                break;
            }
        }

        String commonQuadKey = QuadTileUtils.getMinCommonQuadTileKeyFromLatLong(10, 10, 11, 11);
        assertEquals(expectedCommonQuadKey, commonQuadKey);
    }

    @Test
    public void testGetMinCommonQuadTileKey() {
        String firstQuadKey = QuadTileUtils.getQuadTileKeyFromLatLong(10, 10);
        String secondQuadKey = QuadTileUtils.getQuadTileKeyFromLatLong(11, 11);

        String expectedCommonQuadKey = "";
        int minLength = Math.min(firstQuadKey.length(), secondQuadKey.length());
        for (int i = 0; i < minLength; i++) {
            if (firstQuadKey.charAt(i) != secondQuadKey.charAt(i)) {
                expectedCommonQuadKey = firstQuadKey.substring(0, i);
                break;
            }
        }
        String commonQuadKey = QuadTileUtils.getMinCommonQuadTileKey(firstQuadKey, secondQuadKey);
        assertEquals(expectedCommonQuadKey, commonQuadKey);
    }
}
