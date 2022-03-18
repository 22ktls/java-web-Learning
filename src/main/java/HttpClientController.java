import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpClientController {

    private String url; //接口地址

    /**
     * 无参构造方法
     */
    public HttpClientController() {
    }

    /**
     * 有参构造方法
     * @param url url地址
     */
    public HttpClientController(String url) {
        this.url = url;

    }

    /**
     * 获取url地址
     * @return url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url地址
     * @param url url地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 创建链接获取json数据信息
     * @return json数据对象
     */
    public JSONObject doGet() {
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
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取响应信息实体
                HttpEntity entity = response.getEntity();
                //利用工具将实体信息转换为字符串
                String JsonData = EntityUtils.toString(entity);
                //利用fastjson将字符串信息（json信息）转换为对象
                JSONObject tempObject = JSONObject.parseObject(JsonData);
                //返回json信息对象
                return tempObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭链接
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




}
