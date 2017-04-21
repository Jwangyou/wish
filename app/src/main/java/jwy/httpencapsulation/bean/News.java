package jwy.httpencapsulation.bean;

/**
 * Created by Administrator on 2017/4/20.
 * 返回的数据类型实体类
 * 简单的写几个测试
 */

public class News {
    int id;
    int subLemmaId;
    int newLemmaId;
    String key;
    String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubLemmaId() {
        return subLemmaId;
    }

    public void setSubLemmaId(int subLemmaId) {
        this.subLemmaId = subLemmaId;
    }

    public int getNewLemmaId() {
        return newLemmaId;
    }

    public void setNewLemmaId(int newLemmaId) {
        this.newLemmaId = newLemmaId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
