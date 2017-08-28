/**
 * Copyright(C) 2017 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 *
 */
package com.chen.util;

/**
 * @since 2017年3月29日 下午3:14:18
 * @version $Id: GlobalCode.java 20 2017-04-21 14:41:11Z lml2017 $
 * @author SuShuai
 *
 */
public class GlobalCode {

	// 是否是测试环境
		public static final boolean IS_TEST = false;
		public static final String TESTURL = "http://laimailai.tunnel.ifugle.cn/";
		public static final String REALTURL = "http://121.43.150.79:8088/server/";

		// 所有静态资源均使用此接口获取
		public static final String FILEPATH = "api/public/file?uuid=";

		// 邀请二维码生成地址
		public static final String INVITEPATH = "invite/invite.html?activityId=";
		public static final String QRCODELOGO = "/public/image/logo.png";

		public static final byte USER_STATUS_USEING = 1;
		public static final byte USER_STATUS_NOT_USEING = 2;
		public static final byte LOGIN_ONLINE = 1;
		public static final byte LOGIN_NOT_ONLINE = 2;
		public static final int VALID_TOKEN = 0;
		public static final byte INVALID_TOKEN = 1;
		public static final byte NOT_EQUAL_TOKEN = 3;
		public static final long IMGSIZE = 1024 * 1024 * 5L;
		public static final long VIDEOSIZE = 1024 * 1024 * 50L;

		// 业务类型（2：我的 3：活动 4：训练 5：比赛 ）
		public static final byte BUSINESS_TYPE_TWO = 2;
		public static final byte BUSINESS_TYPE_THREE = 3;
		public static final String BUSINESS_TYPE_THREE_TITLE_SENDER = "活动消息";
		public static final String BUSINESS_TYPE_THREE_TITLE_USER = "活动消息";
		public static final String BUSINESS_TYPE_THREE_TITLE_START = "活动消息";

		public static final byte BUSINESS_TYPE_fOUR = 4;
		public static final byte BUSINESS_TYPE_FIVE = 5;
		public static final String BUSINESS_TYPE_FIVE_TITLE_USER = "比赛消息";

		// 推送类型（0：系统公告，1：订单消息，2：我的 3：活动 4：训练 5：比赛）
		public static final byte PUSH_TYPE_ZERO = 0;
		public static final byte PUSH_TYPE_ONE = 1;
		public static final byte PUSH_TYPE_TWO = 2;

		// 文件保存地址
		public static final String MONGODB_PATH = "dongdong/resourse";

		// 阿里云OSS地址
		public static final String OSS_PATH = "http://diandianshequ.oss-cn-shanghai.aliyuncs.com/";
		public static final String OSS_PJNAME = "diandianshequ/";
		public static final String OSS_AUDIO = "audio/";
		public static final String OSS_IMG = "img/";
		public static final String OSS_VIDEO = "video/";
		public static final String OSS_VIDEO_NAME = ".mp4";
		public static final String OSS_AUDIO_NAME = ".mp3";
		public static final String OSS_IMG_NAME = ".jpg";

		// ----------------------------------测试----------------------------------------------//
		// 支付宝appid
		private static final String APP_ID_TEST = "2017050407111257";
		// 支付宝私钥
		private static final String APP_PRIVATE_KEY_TEST = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAItpSSMnV"
				+ "+nay3KmROSo1OiRY+9EDd7hFtRQilBR/7r0al3e+VsOK/AOwN04UYLwHAJ2fByv63ilDidWWmUZEBpe9QRvr0yf"
				+ "b2RpYb2hTJ2N38uu9k5251z5MSPwCHYz3ZvjqbjpIqAtPu477yaF6h3ypmA4T8Br6Bv7XaUx5fNzAgMBAAECgYEAiJ25"
				+ "hVCFz26Q1p5nfuiQFAR+OTeonBRaSd9PW5yvjab9IVZjZ77AgfQDPgZ1omnoGOXBhF9E0qp070mpNc9Uvf88I077aKCNY8P"
				+ "CvV+pya8svFxU1UVVqyGj220XCCGtys4Mq3bjVR01wf4D3DRNyHQhY/zOgbaLJkuK66lHADkCQQDOehiKOcxoFSdBkrShErU"
				+ "1iJoERcDyp0id1AJdiprXGL+NI0i4eHFW0p+hGhMM9bxflffHFim+WnY0u66b/j69AkEArNlJV51Gm+4TEtLmsd7eD6HcFa4a"
				+ "WWZdP+pc3q0kqwldM5zm4+i2i+tPzC1OyP/wFxnEU0mMAcW8nRKXCKF17wJAFSFzr3m4zJIVoWVVLQLQHrlrIbLNy0eJa/WNhG"
				+ "sx639x4wzQXWieGMeXZJJZjsL7U0DyyaIgBYYWgNEQTpCXdQJAGLcdKJ0BiqxZJ9jmScq/X+baDHOPqvbbIJeHXgPN+XLmU8n8rd"
				+ "wJtioYh+52lIEcOIFjtDERBvPaS29sDzzfGwJAR3z3gZrRZd56EQxJzPCBsmspZxV7mX9jAwh7AnF5weY6kqhf+viOi2xZBZIwCfjt52nch4S/SkF1wXk2mNtDnQ==";
		// 支付宝公钥
		private static final String ALIPAY_PUBLIC_KEY_TEST = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN"
				+ "8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		// ----------------------------------测试----------------------------------------------//

		// ----------------------------------生产----------------------------------------------//

		// 支付宝appid
		private static final String APP_ID = "2017050407111257";
		// 支付宝私钥
		private static final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAItpSSMnV"
				+ "+nay3KmROSo1OiRY+9EDd7hFtRQilBR/7r0al3e+VsOK/AOwN04UYLwHAJ2fByv63ilDidWWmUZEBpe9QRvr0yf"
				+ "b2RpYb2hTJ2N38uu9k5251z5MSPwCHYz3ZvjqbjpIqAtPu477yaF6h3ypmA4T8Br6Bv7XaUx5fNzAgMBAAECgYEAiJ25"
				+ "hVCFz26Q1p5nfuiQFAR+OTeonBRaSd9PW5yvjab9IVZjZ77AgfQDPgZ1omnoGOXBhF9E0qp070mpNc9Uvf88I077aKCNY8P"
				+ "CvV+pya8svFxU1UVVqyGj220XCCGtys4Mq3bjVR01wf4D3DRNyHQhY/zOgbaLJkuK66lHADkCQQDOehiKOcxoFSdBkrShErU"
				+ "1iJoERcDyp0id1AJdiprXGL+NI0i4eHFW0p+hGhMM9bxflffHFim+WnY0u66b/j69AkEArNlJV51Gm+4TEtLmsd7eD6HcFa4a"
				+ "WWZdP+pc3q0kqwldM5zm4+i2i+tPzC1OyP/wFxnEU0mMAcW8nRKXCKF17wJAFSFzr3m4zJIVoWVVLQLQHrlrIbLNy0eJa/WNhG"
				+ "sx639x4wzQXWieGMeXZJJZjsL7U0DyyaIgBYYWgNEQTpCXdQJAGLcdKJ0BiqxZJ9jmScq/X+baDHOPqvbbIJeHXgPN+XLmU8n8rd"
				+ "wJtioYh+52lIEcOIFjtDERBvPaS29sDzzfGwJAR3z3gZrRZd56EQxJzPCBsmspZxV7mX9jAwh7AnF5weY6kqhf+viOi2xZBZIwCfjt52nch4S/SkF1wXk2mNtDnQ==";
		// 支付宝公钥
		private static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN"
				+ "8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		// ----------------------------------生产----------------------------------------------//

		public static final String ALIPAY_SUCCESS = "TRADE_SUCCESS";
		public static final String ALIPAY_FINISHED = "TRADE_FINISHED";
		public static final String PAY_RESULT_SUCCESS = "success";
		public static final String PAY_RESULT_FAIL = "fail";

		public static final String BUY_TRAINING_BODY = "购买训练";
		public static final String BUY_AFTER_TRAINING_BODY = "购买课外训练";

		// 支付宝异步通知地址
		public static final String ALIPAY_NOTIFY_URL = "api/public/alipay/notify";
		// 微信异步通知地址
		public static final String WX_NOTIFY_URL = "api/public/wxPay/notify";

		/*
		 * 获取域名
		 */
		public static String getUrl() {
			if (IS_TEST) {
				return TESTURL;
			} else {
				return REALTURL;
			}
		}

		/*
		 *  获取支付宝异步通知地址
		 */
		public static String getAlipayNotifyUrl() {
			if (IS_TEST) {
				return TESTURL + ALIPAY_NOTIFY_URL;
			} else {
				return REALTURL + ALIPAY_NOTIFY_URL;
			}
		}

		/*
		 *  获取微信异步通知地址
		 */
		public static String getWxNotifyUrl() {
			if (IS_TEST) {
				return TESTURL + WX_NOTIFY_URL;
			} else {
				return REALTURL + WX_NOTIFY_URL;
			}
		}

		/*
		 * 获取支付宝appid
		 */
		public static String getAlipayAppId() {
			if (IS_TEST) {
				return APP_ID_TEST;
			} else {
				return APP_ID;
			}
		}

		/*
		 * 获取支付宝私钥
		 */
		public static String getAlipayPrivateKey() {
			if (IS_TEST) {
				return APP_PRIVATE_KEY_TEST;
			} else {
				return APP_PRIVATE_KEY;
			}
		}

		/*
		 * 获取支付宝私钥
		 */
		public static String getAlipayPublicKey() {
			if (IS_TEST) {
				return ALIPAY_PUBLIC_KEY_TEST;
			} else {
				return ALIPAY_PUBLIC_KEY;
			}
		}
}
