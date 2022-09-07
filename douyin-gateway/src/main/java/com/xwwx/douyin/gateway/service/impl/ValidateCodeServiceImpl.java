package com.xwwx.douyin.gateway.service.impl;

import com.google.code.kaptcha.Producer;
import com.xwwx.douyin.common.core.constant.Constants;
import com.xwwx.douyin.common.core.domain.R;
import com.xwwx.douyin.common.core.exception.ServiceException;
import com.xwwx.douyin.common.core.utils.IdUtils;
import com.xwwx.douyin.common.core.utils.StringUtils;
import com.xwwx.douyin.common.core.utils.sign.Base64;
import com.xwwx.douyin.common.redis.service.RedisService;
import com.xwwx.douyin.gateway.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 可乐罐
 * @date: 2022/1/6 9:59
 * @description:
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;
    @Override
    public R createCapcha() throws Exception{
        R r = R.ok();
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = "math";
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        //redis存放2分钟
        redisService.set(verifyKey, code, Constants.CAPTCHA_EXPIRATION);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e) {
            return R.error(e.getMessage());
        }

        Map map = new HashMap();
        map.put("uuid", uuid);
        map.put("img", Base64.encode(os.toByteArray()));
        r.setData(map);
        return r;
    }

    @Override
    public void checkCapcha(String code, String uuid) throws Exception{
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new ServiceException("验证码已失效");
        }
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = (String)redisService.get(verifyKey);
        //redisService.del(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException("验证码错误");
        }
    }
}