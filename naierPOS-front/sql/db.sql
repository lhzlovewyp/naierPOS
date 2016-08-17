CREATE DATABASE `db_tdct` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `tuser` (
  `userId` int NOT NULL AUTO_INCREMENT, ##用户ID
  `userName` varchar(45) NOT NULL, ##用户名(email)
  `nickName` varchar(45) NOT NULL, ##昵称
  `password` varchar(45) NOT NULL, ##密码
  `createDate` datetime NOT NULL, ##创建日期
  `lastLoginDate` datetime DEFAULT NULL, ##登陆日期
  `ip` varchar(45) DEFAULT NULL, ##IP
  `photo` varchar(45) DEFAULT NULL, ##头像
  `qq` varchar(45) DEFAULT NULL, ##QQ
  `locked` tinyint(4) DEFAULT '0', ##锁定 1锁定 0未锁定
  `token` varchar(45) DEFAULT NULL, ##token
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbusiness` (
  `bussinessId` INT NOT NULL AUTO_INCREMENT, ##ID
  `gameName` VARCHAR(100) NOT NULL, ##名称
  `enName` VARCHAR(45) NULL, ##英文名称
  `typeCode` VARCHAR(10) NOT NULL, ##游戏类型
  `platformCode` VARCHAR(10) NOT NULL, ##平台
  `editionCode` VARCHAR(10) NULL, ##版本
  `price` DECIMAL(9,2) NULL, #价格
  `qualityCode` TINYINT NOT NULL, #成色
  `qq` VARCHAR(15) NULL , ##qq
  `mobilephone` VARCHAR(20) NULL ,
  `maker` VARCHAR(45) NULL, ##开发商
  `publisher` VARCHAR(45) NULL, ##发行商
  `businessTypeCode` varchar(10) NOT NULL ,#交易类型
  `tradingWayCode` VARCHAR(10) NULL, ##交易方式
  `location` VARCHAR(10) NULL,##发布者所在地
  `img` VARCHAR(100) NULL,##图片路劲
  `description` TINYTEXT NULL ,
  `createDate` TIMESTAMP NOT NULL,
  `modifyDate` TIMESTAMP NOT NULL,
  `creator` VARCHAR(10) NOT NULL,
  `level` TINYINT NOT NULL  DEFAULT 0,#级别（通知，置顶等）
  PRIMARY KEY (`bussinessId`));

	CREATE TABLE `dictionary` (
	  `dictId` INT NOT NULL AUTO_INCREMENT, ##字典ID
	  `category` VARCHAR(45) NOT NULL, ##类别
	  `name` VARCHAR(45) NOT NULL, ##名字
	  `code` VARCHAR(45) NOT NULL, ##代码
	  `enable` INT NOT NULL DEFAULT 1, ##可用 0不可用 1可用
	  `desc` VARCHAR(100) NULL, ##描述
	  PRIMARY KEY (`dictId`));



