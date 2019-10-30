package cc.mrbird.febs.gen.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author lxj
 * @date 2019-10-24 10:15:28
 */
@Data
@TableName("banner")
public class Banner {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 描述
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 跳转地址
     */
    @TableField("URL")
    private String url;

    /**
     * 序号
     */
    @TableField("PRIORITY")
    private Integer priority;

    /**
     * 图片类型 1bannar 2登录页 3热门活动 4公告 5.微信广告 6.翼支付广告 7，余额支付广告 8.预付费卡广告
     */
    @TableField("PICTURE_TYPE")
    private Integer pictureType;

    /**
     * 标题
     */
    @TableField("NAME")
    private String name;

    /**
     * 有效日期 （格式如：1992-09-09 12：00：00）
     */
    @TableField("EFFECTIVE_DATE")
    private String effectiveDate;

    /**
     * 是否有效  0否  1是
     */
    @TableField("IS_VALID")
    private Integer isValid;

    /**
     * 是否全局 0 否 1 是
     */
    @TableField("IS_OVERALL")
    private Integer isOverall;

}