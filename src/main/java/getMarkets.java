import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class getMarkets {


    public static void zebApi() {
        Response response;
        response = given().contentType(ContentType.JSON).when().get("https://www.zebapi.com/pro/v1/market/");
        JSONArray ja = new JSONArray(response.getBody().asString());
        for (int i = 0; i < ja.length(); i++) {
            try {
                System.out.println(ja.getJSONObject(i).get("buy"));
                System.out.println(ja.getJSONObject(i).get("sell"));
                System.out.println(ja.getJSONObject(i).get("pair"));
            } catch (JSONException Ignored) {
            }
        }
    }

    public static void bitBns() {

        Response response;
        response = given().contentType(ContentType.JSON).when().get("https://bitbns.com/order/fetchTickers");
        JSONObject jo = new JSONObject(response.getBody().asString());
//        JSONArray jo1 = new JSONArray(jo);
        System.out.println(jo);
        for (int i = 0; i < jo.length(); i++) {

            try {
                System.out.println(jo.getJSONArray(String.valueOf(i)));
                System.out.println(jo.getJSONObject(String.valueOf(i)).get("sell"));
                System.out.println(jo.getJSONObject(String.valueOf(i)).get("pair"));
            } catch (JSONException Ignored) {
            }
        }
    }

    public static void wazirx() {
        Response response;
        response = given().contentType(ContentType.JSON).when().get("https://api.wazirx.com/api/v2/tickers");
        JSONObject jo = new JSONObject(response.getBody().asString());
        System.out.println(jo);
    }

    public static void computeArbitrage() {
        Response zebResponse, bitbnsResponse;
        zebResponse = given().contentType(ContentType.JSON).when().get("https://www.zebapi.com/pro/v1/market/");
        bitbnsResponse = given().contentType(ContentType.JSON).when().get("https://bitbns.com/order/fetchTickers");


        JSONArray zeb = new JSONArray(zebResponse.getBody().asString());
        JSONObject bit = new JSONObject(bitbnsResponse.getBody().asString());
//        JSONObject waz = new JSONObject(wazirxReposponse.getBody().asString());

        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("\"" + "\"", new Object[]{"PAIR", "BitBns Buy", "BitBns Sell", "Zebpay Buy", "Zebpay Sell",
                "ZEBPAYtoBITBNS", "ZEBPAYtoBITBNS_percent", "BITBNStoZEBPAY", "BITBNStoZEBPAY_percent"});

        for (int i = 0; i < zeb.length(); i++) {
            try {
                String pair = zeb.getJSONObject(i).get("pair").toString().replace("-", "/");
                if (bitbnsResponse.getStatusCode() == 200) {

                    if (bit.has(pair)) {
                        BigDecimal big1 = new BigDecimal(bit.getJSONObject(pair).getJSONObject("info").get("highest_buy_bid").toString());
                        Double bitBuy = big1.doubleValue();
                        BigDecimal big2 = new BigDecimal(bit.getJSONObject(pair).getJSONObject("info").get("lowest_sell_bid").toString());
                        Double bitSell = big2.doubleValue();
                        BigDecimal big3 = new BigDecimal(zeb.getJSONObject(i).get("sell").toString());
                        Double zebBuy = big3.doubleValue();
                        BigDecimal big4 = new BigDecimal(zeb.getJSONObject(i).get("buy").toString());
                        Double zebSell = big4.doubleValue();
                        Double ZEBPAYtoBITBNS_Diff = getDifference(zebSell,bitBuy);
                        Double ZEBPAYtoBITBNS_percent = getDiffPercentage(ZEBPAYtoBITBNS_Diff,zebSell);
                        Double BITBNStoZEBPAY_Diff = getDifference(bitSell,zebBuy);
                        Double BITBNStoZEBPAY_percent = getDiffPercentage(BITBNStoZEBPAY_Diff,bitSell);
                        data.put("\"" + i + "\"", new Object[]{pair, bitBuy.toString(), bitSell.toString(), zebBuy.toString(), zebSell.toString(),
                                ZEBPAYtoBITBNS_Diff.toString(), ZEBPAYtoBITBNS_percent.toString(), BITBNStoZEBPAY_Diff.toString(), BITBNStoZEBPAY_percent.toString()});
                        writeExcel(data);
                    }
                }
            } catch (Exception ignored) { /*e.printStackTrace();*/}
        }
    }

    public static void arbitrageWazirxToBitBns(){
        Response wazirxReposponse;
        wazirxReposponse = given().contentType(ContentType.JSON).when().get("https://api.wazirx.com/api/v2/tickers");

    }

    public static Double getDifference(Double buy, Double sell){
        Double diff = sell - buy;
        return diff;
    }
    public static Double getDiffPercentage(Double diff, Double buy){
        Double percentage = (diff) / buy * 100;
        return percentage;
    }
    public static void writeExcel(Map<String, Object[]> data) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("arbitrage");
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                // this line creates a cell in the next column of that row
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    ((Cell) cell).setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File("arbitrage.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("arbitrage.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        //getMarkets.wazirx();
        //getMarkets.bitBns();
        getMarkets.computeArbitrage();
    }

}
