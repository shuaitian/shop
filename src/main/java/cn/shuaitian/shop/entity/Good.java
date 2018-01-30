package cn.shuaitian.shop.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 */
public class Good implements Serializable {
    private Integer id;
    
    @Length(min=2,max=80,message="标题长度不符合要求")
    private String title;
    
    @Length(min=2,max=140 ,message="摘要长度不符合要求")
    private String summary;
    
    @NotEmpty(message="图片不能为空")
    private String picurl;
    
    private String indexImg;
    
    @Length(min=2,max=10000,message="内容长度不符合要求")
    private String content;
    
    //priceStr用于接收string类型的price属性
    @NotEmpty(message="价格不能为空")
    private String priceStr;
    
    private Integer price;

	private Date createtime;
	
	private Integer status;
	
	private Integer selledCount;

    private static final long serialVersionUID = 1L;
    
    
    
    
    public Integer getSelledCount() {
		return selledCount;
	}

	public void setSelledCount(Integer selledCount) {
		this.selledCount = selledCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIndexImg() {
		return indexImg;
	}

	public void setIndexImg(String indexURL) {
		this.indexImg = indexURL;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Good other = (Good) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getPicurl() == null ? other.getPicurl() == null : this.getPicurl().equals(other.getPicurl()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getPicurl() == null) ? 0 : getPicurl().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

	@Override
	public String toString() {
		return "Good [id=" + id + ", title=" + title + ", summary=" + summary + ", picurl=" + picurl + ", content="
				+ content + ", priceStr=" + priceStr + ", price=" + price + ", createtime=" + createtime + "]";
	}


}