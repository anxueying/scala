package com.atguigu.charpter5;

/**
 * @author Shelly An
 * @create 2020/7/25 16:36
 */
public class PMTJsonRootBean {
    //可以只写自己需要的字段
    //字段类型一定要一致，否则解析不了后面就都乱了

    private String ip;

    private String adplatformproviderid ;
    private String requestmode;
    private String processnode;
    private String iseffective;
    private String isbilling;
    private String isbid;
    private String iswin;
    private String adorderid;
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAdplatformproviderid() {
        return adplatformproviderid;
    }

    public void setAdplatformproviderid(String adplatformproviderid) {
        this.adplatformproviderid = adplatformproviderid;
    }

    public String getRequestmode() {
        return requestmode;
    }

    public void setRequestmode(String requestmode) {
        this.requestmode = requestmode;
    }

    public String getProcessnode() {
        return processnode;
    }

    public void setProcessnode(String processnode) {
        this.processnode = processnode;
    }

    public String getIseffective() {
        return iseffective;
    }

    public void setIseffective(String iseffective) {
        this.iseffective = iseffective;
    }

    public String getIsbilling() {
        return isbilling;
    }

    public void setIsbilling(String isbilling) {
        this.isbilling = isbilling;
    }

    public String getIsbid() {
        return isbid;
    }

    public void setIsbid(String isbid) {
        this.isbid = isbid;
    }

    public String getIswin() {
        return iswin;
    }

    public void setIswin(String iswin) {
        this.iswin = iswin;
    }

    public String getAdorderid() {
        return adorderid;
    }

    public void setAdorderid(String adorderid) {
        this.adorderid = adorderid;
    }

    public String getAdcreativeid() {
        return adcreativeid;
    }

    public void setAdcreativeid(String adcreativeid) {
        this.adcreativeid = adcreativeid;
    }

    private String adcreativeid         ;

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "PMTJsonRootBean{" +
                "ip='" + ip + '\'' +
                ", adplatformproviderid='" + adplatformproviderid + '\'' +
                ", requestmode='" + requestmode + '\'' +
                ", processnode='" + processnode + '\'' +
                ", iseffective='" + iseffective + '\'' +
                ", isbilling='" + isbilling + '\'' +
                ", isbid='" + isbid + '\'' +
                ", iswin='" + iswin + '\'' +
                ", adorderid='" + adorderid + '\'' +
                ", province='" + province + '\'' +
                ", adcreativeid='" + adcreativeid + '\'' +
                '}';
    }
}