/**
 * 商品
 */
public class Product {
    private String name;//商品名字
    private float price;//商品价格
    private float market_price;//商品原价
    private String spec;//商品规格
    private String share_content;//商品内容

    /**
     * 无参商品构造方法
     */
    public Product() {
    }

    /**
     * 有参商品构造方法
     * @param name 商品名
     * @param price 商品价
     * @param market_price 商品原价
     * @param spec 商品规格
     * @param share_content 商品内容
     */
    public Product(String name, float price, float market_price, String spec, String share_content) {
        this.name = name;
        this.price = price / 100;
        this.market_price = market_price / 100;
        this.spec = spec;
        this.share_content = share_content;
    }

    /**
     * 获取商品名
     * @return 商品名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置商品名
     * @return 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品价
     * @return 商品价
     */
    public float getPrice() {
        return price;
    }
    /**
     * 设置商品价
     * @return 商品价
     */
    public void setPrice(float price) {
        this.price = price / 100;
    }

    /**
     * 获取原价
     * @return 原价价
     */
    public float getMarket_price() {
        return market_price;
    }

    /**
     * 设置原价
     * @return 原价价
     */
    public void setMarket_price(float market_price) {
        this.market_price = market_price / 100;
    }
    /**
     * 获得规格
     * @return 规格
     */
    public String getSpec() {
        return spec;
    }
    /**
     * 设置规格
     * @return 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获得分享信息
     * @return 分享信息
     */
    public String getShare_content() {
        return share_content;
    }

    /**
     * 设置分享信息
     * @return 分享信息
     */
    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    /**
     * 输出商品字段
     * @return 商品全部信息
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(this.spec);
        stringBuilder.append(this.market_price);
        stringBuilder.append(this.price);
        stringBuilder.append(this.share_content);
        return stringBuilder.toString();
    }
}
