
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Timer;
import java.util.TimerTask;


public class HttpGetTest {


    public static void main(String[] args) throws IOException {


        //设置爬取的接口
        String url = "https://j1.pupuapi.com/client/product/storeproduct/detail/7c1208da-907a-4391-9901-35a60096a3f9/f883cc75-7597-4c7a-a420-6ce5aa7fe2ed";

        //获得数据
        String data = doGet(url);
        
        //获取关键key
        String specKey = "spec";
        String priceKey="price";
        String nameKey="name";
        String marketPriceKey="market_price";
        String shareContentKey="share_content";

        //将数据转换为对象
        JSONObject jsonObject = JSONObject.parseObject(data);

        //
        String productName = jsonObject.getString(nameKey);
        String spec = jsonObject.getString(specKey);
        Float price = jsonObject.getFloat(priceKey)/100;
        Float marketPrice = jsonObject.getFloat(marketPriceKey)/100;
        String content = jsonObject.getString(shareContentKey);

        System.out.println("-----------商品："+productName+"------------------");
        System.out.println("规格："+spec);
        System.out.println("价格："+price);
        System.out.println("原价/折扣价："+marketPrice+"/"+price);
        System.out.println("详细内容："+content);
        System.out.println("-------------\""+productName+"\"的价格波动--------------");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CheckCurrentPrice(priceKey,url);

            }
        },5000,5000);

    }

    //创建连接池
    public static String doGet(String url) {
        //设置httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求
        HttpGet httpGet = new HttpGet(url);
        //创建响应容器
        CloseableHttpResponse response = null;
        try {
            //执行请求，获取响应数据
            response = httpClient.execute(httpGet);

            //当状态代码为200时，则表面获取成功
            if (response.getStatusLine().getStatusCode()==200){
                //获取响应信息实体
                HttpEntity entity = response.getEntity();
                //利用工具将实体信息转换为字符串
                String JsonData = EntityUtils.toString(entity);
                //利用fastjson将字符串信息（json信息）转换为对象
                JSONObject jsonObject = JSONObject.parseObject(JsonData);
                //获取jsonObJect对象的一个主要的属性信息
                String data = jsonObject.getString("data");
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void CheckCurrentPrice(String priceKey,String url){

        String data = doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(data);
        Float price = jsonObject.getFloat(priceKey)/100;
        LocalDateTime now = LocalDateTime.now();

        String format = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(now);

        LocalTime localTime = now.toLocalTime();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("当前时间为");
        stringBuilder.append(format);
        stringBuilder.append(" "+localTime);
        stringBuilder.append(",价格为");
        stringBuilder.append(price);

        System.out.println(stringBuilder);
    }
}
