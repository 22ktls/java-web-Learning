
import com.alibaba.fastjson.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class HttpGetTest {


    public static void main(String[] args){
        //设置url地址
        String url ="https://j1.pupuapi.com/client/product/storeproduct/detail/7c1208da-907a-4391-9901-35a60096a3f9/f883cc75-7597-4c7a-a420-6ce5aa7fe2ed";
        //创建httpclient控制器
        HttpClientController httpCC = new HttpClientController(url);
        //创建信息处理器
        HttpGetTest httpGetTest = new HttpGetTest();
        //获得商品的json信息
        JSONObject object = httpCC.doGet();
        //利用json数据创建商品对象
        Product product = httpGetTest.estaProduct(object);
        //显示商品信息
        httpGetTest.showProductInfo(product);
        //设置时间任务，每隔5s时间执行查询价格的方法（5000ms<->5s）
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                httpGetTest.currentPrice(httpCC);
            }
        },1000,5000);

    }

    /**
     * 商品信息显示
     * @param product 商品对象
     */
    public void showProductInfo(Product product){
        System.out.println("-----------商品：" + product.getName() + "------------------");
        System.out.println("规格：" + product.getSpec());
        System.out.println("价格：" + product.getPrice());
        System.out.println("原价/折扣价：" + product.getMarket_price()+ "/" + product.getPrice());
        System.out.println("详细内容：" + product.getShare_content());
        System.out.println("-----------\""+product.getName()+"\" 的价格波动--------------------");
    }

    /**
     * 建立商品对象
     * @param jsonObject json信息
     * @return 商品对象
     */
    public Product estaProduct(JSONObject jsonObject) {

        //获取信息数据
        String data = jsonObject.getString("data");
        //建立商品对象
        Product product = JSONObject.parseObject(data, Product.class);
        //返回商品对象
        return product;

    }

    /**
     * 查询当前时间价格
     * @param httpCC httpclient控制器
     */
    private  void currentPrice(HttpClientController httpCC){
        //获得json数据对象
        JSONObject object = httpCC.doGet();
        //建立商品对象
        Product product = estaProduct(object);
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //设置转换后的格式
        String strPattern="yyyy年MM月dd日 HH点mm分ss秒";
        //赋予时间转换器转换格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strPattern);
        //转换时间
        String timeOfZHCN = formatter.format(now);
        //输出
        System.out.println("当前时间为"+timeOfZHCN+",价格为"+product.getPrice());

    }





}
