
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;



public class HttpGetTest {


    public static void main(String[] args) throws IOException {

        PoolingHttpClientConnectionManager httpClientCM=new PoolingHttpClientConnectionManager();
        doGet(httpClientCM);
    }

    //创建连接池
    public static void doGet(PoolingHttpClientConnectionManager poolingHttpClient){
        //设置httpclient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClient).build();

        //设置爬取的接口
        String url = "https://j1.pupuapi.com/client/product/storeproduct/detail/7c1208da-907a-4391-9901-35a60096a3f9/f883cc75-7597-4c7a-a420-6ce5aa7fe2ed";

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
