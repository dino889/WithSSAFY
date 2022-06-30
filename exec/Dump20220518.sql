-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: k6d201.p.ssafy.io    Database: withssafy
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1297);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequences`
--

DROP TABLE IF EXISTS `sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sequences` (
  `NAME` varchar(32) DEFAULT NULL,
  `CURRVAL` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequences`
--

LOCK TABLES `sequences` WRITE;
/*!40000 ALTER TABLE `sequences` DISABLE KEYS */;
INSERT INTO `sequences` VALUES ('Test',54340);
/*!40000 ALTER TABLE `sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_board`
--

DROP TABLE IF EXISTS `tbl_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_board` (
  `id` bigint NOT NULL,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `write_dt` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKskpligyvfp0pmm28rvqwn6hw3` (`type_id`),
  KEY `FK1akb3frnoq5mgmefm7wwxa8n0` (`user_id`),
  CONSTRAINT `FK1akb3frnoq5mgmefm7wwxa8n0` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKskpligyvfp0pmm28rvqwn6hw3` FOREIGN KEY (`type_id`) REFERENCES `tbl_board_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_board`
--

LOCK TABLES `tbl_board` WRITE;
/*!40000 ALTER TABLE `tbl_board` DISABLE KEYS */;
INSERT INTO `tbl_board` VALUES (563,'우리집 귀요미','0017931788','빨리 구경하고 가세요','1652250077947',2,177),(669,'우리 회사 올 사람있어? 백엔드 한명 구한다는데','','이직 준비하는 사람?','1652406288718',3,195),(672,'휴대폰 팜~ 갤럭시 S8+','','모바일반 필독, 휴대폰팜','1652406322631',5,195),(892,'진짜야? 진짜 기가막힌다. 이제 싸피라며 동문회 이런거 활성화 되기 딱 좋은 것 같아 ㅋㅋ','','이 어플 만들 애들 싸피라며?','1652422889377',1,657),(900,'팀원들이랑 갈등 생기면 어떻게 해결함..? 너무 궁금하다 다른 팀들은 다 잘 맞는지!!','','프로젝트 할 때','1652423020018',1,657),(901,'나 넘모 보고싶은데 싸피한다고 자취하니까 같이 갈 사람이 없어 ㅡㅡ','','닥터스트레인지2 같이 보러 갈 사람~~~','1652423053970',1,657),(902,'상태 깨끗하고 좋아요 공룡책 비전공자들 공부할때 좋습니다.','0017932073','운영체제 공룡책 팝니다.','1652423217451',5,657),(907,'여태 활동하면서 쿠폰 많이 받았는데 기간안에 다 못쓰겠어서 나눔합니다. 대신 나랑 같이 먹어줘야함','','[나눔] 싸피벅스 쿠폰 무료나눔','1652424168736',5,657),(920,'- 안드로이드\r\n- ios\r\n\r\n점점 둘이 닮아감 -> 두 가지를 한 번에 개발하는 방법은 없을까?\r\n\r\n-> 웹페이지 형식으로 만들면 어때? -> 하이브리드 앱\r\n\r\n### 하이브리드 앱(웹페이지+네이티브)\r\n\r\n스마트폰의 하드웨어를 사용할 수 있음(카메라, gps)\r\n\r\n#### 프레임워크','','모바일 앱, 어떤 프레임워크를 사용해야할까?','1652424381756',6,657),(921,'열정적이고 싸피에 진심인 친구들을 모집합니다. 공지사항을 확인하시고 지원해주세요!','','[모집] 싸피셜 모집합니다','1652424514662',4,657),(922,'나 스터디 할건데 CS스터디 할꺼야 같이 할 사람?','','스터디 같이 할 사람???','1652424541103',4,657),(925,'오늘 지각함. ㅡㅡ 결국 노래부름\nWebex상태 이상해서 지각했는데 어쩔수없지 신나게 부르고 끼 발산해본다. 이런 생각으로 그냥 열심히 부르니까 다들 좋아하더라 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 근데 너무 부끄러움 ㄹㅇ\n','','ㅋㅋㅋㅋ 오늘 지각해서 노래부른 썰 푼다','1652424788970',1,657),(926,'프로젝트 할 때마다 옆에서 도와주는 내 페어에게 고맙고 미안하다~~~!!!!!!!!!!!!!','','넌 나의 마니또❤️','1652424874511',2,657),(927,'멀리서 넘어진거 보였는데 민망할까봐 모른척 했음, 아파보이더라 ㄱㅊ음?','','오늘 구미 멀캠에서 넘어지신분','1652424948924',2,657),(928,'내 뱃속에 그지같은게 사는지 하루종일 배고픔 진짜, 화날라그래.','','그지같은게...','1652425015707',2,657),(929,'싸피끝나고 취업 성공했는데 이제 싸피가 그립다. 취업전에는 취업하고 싶어서 미치는줄 알았는데 ㅠㅠㅠㅠ','','취업 만족함?','1652425065064',3,657),(930,'나 비대면 수업만 받아서 동문회 참석하고 싶은데 언제부터 할까 벌써 신난다 ㅋㅋㅋㅋ','','동문회 어디서 해?','1652425093211',3,657),(931,'나 싸피셜 열심히 했는데 커뮤니티 따로 있으면 좋겠다 서로 소통하고 기사 주제 공유하고 이러면 편할텐데, 건의해봐?','','싸피셜 커뮤니티 있음 좋겠다','1652425131595',3,657),(932,'진짜 우리회사 쥐꼬리만큼 줌 ㅡㅡ 이직할거임','','너네들 연봉 얼마 받아?','1652425158526',3,657),(934,'빌드 자동으로 해주니깐 개꿀~~~','','jenkins 사용하세요~~','1652425202144',6,657),(935,'1. 객체의 생성에도 유의미한 이름을 사용하라.\r\n2. 함수는 하나의 역할만 해야한다.\r\n3. 명령과 조회를 분리하라 (Command와 Query의 분리)\r\n4. 오류코드 보다는 예외를 활용하자.\r\n5. 여러 예외가 발생하는 경우 Wrapper 클래스로 감싸자.\r\n6. 테스트 코드의 작성.\r\n7. 클래스의 최소화.\r\n8. 클래스의 응집도(높은 응집도와 낮은 결합도)\r\n9. 변경하기 쉬운 클래스\r\n10. 설계 품질을 높여주는 4가지 규칙\r\n10-1. 모든 테스트를 실행하라.\r\n10-2. 중복을 제거하라.\r\n10-3. 프로그래머의 의도를 표현하라.\r\n10-4. 클래스와 메소드의 수를 최소로 줄여라.\r\n11. 디미터 법칙 : 어떤 모듈이 호출하는 객체의 속사정을 몰라야 한다.\r\n그렇기에 객체는 자료를 숨기고 함수를 공개해야 한다.','','클린코드 원칙','1652425400799',6,657),(936,'가독성 \r\n\r\n누구든지 코드를 쉽게 읽을 수 있도록 작성\r\n\r\n코드 작성 시 이해하기 쉬운 용어를 사용하거나 들여쓰기 기능 등을 사용\r\n\r\n단순성\r\n\r\n코드를 간단하게 작성\r\n\r\n한 번에 한 가지를 처리하도록 코드를 작성하고 클래스/메소드/함수 등을 최소 단위로 분리\r\n\r\n의존성 배제 \r\n\r\n코드가 다른 모듈에 미치는 영향을 최소화\r\n\r\n코드 변경 시 다른 부분에 영향이 없도록 작성\r\n\r\n중복성 최소화 \r\n\r\n코드의 중복을 최소화\r\n\r\n중복된 코드는 삭제하고 공통된 코드를 사용\r\n\r\n추상화\r\n\r\n상위 클래스/메소드/함수에서는 간략하게 애플리케이션의 특성을 나타내고, 상세 내용은 하위 클래스/메소드/함수에서 구현','','소스코드 최적화','1652425435904',6,657),(937,'나 필요없어 싸게 팜 제시 ㄱㄱ','','네트워크 통신 책 팜','1652425528553',5,657),(939,'기타 5만원에 팝니다. 구미와서 한번도 안쳤쪙','','기타 팝니다','1652425591320',5,657),(956,'오늘 취업함 축하해줘','','ㅎㅇㅎㅇ','1652425853938',1,441),(964,'개봉일 : 6월 15일\n기대된다','','마녀2','1652425948867',6,441),(1094,'이름은 또롱이입니당 귀엽쥬~><!!,','','저희집 강아지 자랑해요ㅎㅎ!!♡','1652669431535',1,53),(1247,'형배의 늠름한 자세 좀 보고가세여 ~!~! 아주 누굴 닮아서 이렇게 거만한건지 ..','0000000011','사나이 최형배','1652705660607',1,177);
/*!40000 ALTER TABLE `tbl_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_board_type`
--

DROP TABLE IF EXISTS `tbl_board_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_board_type` (
  `id` bigint NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_board_type`
--

LOCK TABLES `tbl_board_type` WRITE;
/*!40000 ALTER TABLE `tbl_board_type` DISABLE KEYS */;
INSERT INTO `tbl_board_type` VALUES (1,'자유 게시판'),(2,'비밀 게시판'),(3,'수료생 게시판'),(4,'홍보 게시판'),(5,'장터 게시판'),(6,'정보 게시판');
/*!40000 ALTER TABLE `tbl_board_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_class_manager`
--

DROP TABLE IF EXISTS `tbl_class_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_class_manager` (
  `id` bigint NOT NULL,
  `classroom_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdahp4q0npuxb4plcayipxse7e` (`classroom_id`),
  KEY `FKqjw2grpwkvd6mtcfcto1a3dip` (`user_id`),
  CONSTRAINT `FKdahp4q0npuxb4plcayipxse7e` FOREIGN KEY (`classroom_id`) REFERENCES `tbl_classroom` (`id`),
  CONSTRAINT `FKqjw2grpwkvd6mtcfcto1a3dip` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_class_manager`
--

LOCK TABLES `tbl_class_manager` WRITE;
/*!40000 ALTER TABLE `tbl_class_manager` DISABLE KEYS */;
INSERT INTO `tbl_class_manager` VALUES (158,6,156),(164,6,162),(167,6,165),(497,15,495),(506,7,504),(509,7,507),(527,8,507),(534,8,532),(551,7,549),(574,8,572),(766,8,764),(770,8,768);
/*!40000 ALTER TABLE `tbl_class_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_classroom`
--

DROP TABLE IF EXISTS `tbl_classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_classroom` (
  `id` bigint NOT NULL,
  `area` varchar(20) DEFAULT NULL,
  `class_description` varchar(100) DEFAULT NULL,
  `generation` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_classroom`
--

LOCK TABLES `tbl_classroom` WRITE;
/*!40000 ALTER TABLE `tbl_classroom` DISABLE KEYS */;
INSERT INTO `tbl_classroom` VALUES (6,'구미','자율 1반',6),(7,'구미','자율 1반',6),(8,'구미','자율 2반',6),(10,'서울','자율 1반',6),(11,'서울','자율 2반',6),(12,'서울','자율 3반',6),(13,'서울','자율 4반',6),(14,'서울','자율 5반',6),(15,'대전','자율 1반',6),(16,'대전','자율 2반',6),(17,'광주','자율 2반',6),(18,'광주','자율 1반',6),(19,'부울경','자율 1반',6),(20,'부울경','자율 2반',6),(272,'전체','전체',6),(273,'구미','전체',6),(274,'광주','전체',6),(275,'서울','전체',6),(276,'대전','전체',6),(277,'부울경','전체',6),(54201,'구미','자율 1반',1),(54202,'구미','자율 1반',1),(54203,'구미','자율 2반',1),(54204,'서울','자율 1반',1),(54205,'서울','자율 2반',1),(54206,'서울','자율 3반',1),(54207,'서울','자율 4반',1),(54208,'서울','자율 5반',1),(54209,'대전','자율 1반',1),(54210,'대전','자율 2반',1),(54211,'광주','자율 2반',1),(54212,'광주','자율 1반',1),(54213,'부울경','자율 1반',1),(54214,'부울경','자율 2반',1),(54215,'전체','전체',1),(54216,'구미','전체',1),(54217,'광주','전체',1),(54218,'서울','전체',1),(54219,'대전','전체',1),(54220,'부울경','전체',1),(54221,'구미','자율 1반',2),(54222,'구미','자율 1반',2),(54223,'구미','자율 2반',2),(54224,'서울','자율 1반',2),(54225,'서울','자율 2반',2),(54226,'서울','자율 3반',2),(54227,'서울','자율 4반',2),(54228,'서울','자율 5반',2),(54229,'대전','자율 1반',2),(54230,'대전','자율 2반',2),(54231,'광주','자율 2반',2),(54232,'광주','자율 1반',2),(54233,'부울경','자율 1반',2),(54234,'부울경','자율 2반',2),(54235,'전체','전체',2),(54236,'구미','전체',2),(54237,'광주','전체',2),(54238,'서울','전체',2),(54239,'대전','전체',2),(54240,'부울경','전체',2),(54241,'구미','자율 1반',3),(54242,'구미','자율 1반',3),(54243,'구미','자율 2반',3),(54244,'서울','자율 1반',3),(54245,'서울','자율 2반',3),(54246,'서울','자율 3반',3),(54247,'서울','자율 4반',3),(54248,'서울','자율 5반',3),(54249,'대전','자율 1반',3),(54250,'대전','자율 2반',3),(54251,'광주','자율 2반',3),(54252,'광주','자율 1반',3),(54253,'부울경','자율 1반',3),(54254,'부울경','자율 2반',3),(54255,'전체','전체',3),(54256,'구미','전체',3),(54257,'광주','전체',3),(54258,'서울','전체',3),(54259,'대전','전체',3),(54260,'부울경','전체',3),(54261,'구미','자율 1반',4),(54262,'구미','자율 1반',4),(54263,'구미','자율 2반',4),(54264,'서울','자율 1반',4),(54265,'서울','자율 2반',4),(54266,'서울','자율 3반',4),(54267,'서울','자율 4반',4),(54268,'서울','자율 5반',4),(54269,'대전','자율 1반',4),(54270,'대전','자율 2반',4),(54271,'광주','자율 2반',4),(54272,'광주','자율 1반',4),(54273,'부울경','자율 1반',4),(54274,'부울경','자율 2반',4),(54275,'전체','전체',4),(54276,'구미','전체',4),(54277,'광주','전체',4),(54278,'서울','전체',4),(54279,'대전','전체',4),(54280,'부울경','전체',4),(54281,'구미','자율 1반',5),(54282,'구미','자율 1반',5),(54283,'구미','자율 2반',5),(54284,'서울','자율 1반',5),(54285,'서울','자율 2반',5),(54286,'서울','자율 3반',5),(54287,'서울','자율 4반',5),(54288,'서울','자율 5반',5),(54289,'대전','자율 1반',5),(54290,'대전','자율 2반',5),(54291,'광주','자율 2반',5),(54292,'광주','자율 1반',5),(54293,'부울경','자율 1반',5),(54294,'부울경','자율 2반',5),(54295,'전체','전체',5),(54296,'구미','전체',5),(54297,'광주','전체',5),(54298,'서울','전체',5),(54299,'대전','전체',5),(54300,'부울경','전체',5),(54301,'구미','자율 1반',7),(54302,'구미','자율 1반',7),(54303,'구미','자율 2반',7),(54304,'서울','자율 1반',7),(54305,'서울','자율 2반',7),(54306,'서울','자율 3반',7),(54307,'서울','자율 4반',7),(54308,'서울','자율 5반',7),(54309,'대전','자율 1반',7),(54310,'대전','자율 2반',7),(54311,'광주','자율 2반',7),(54312,'광주','자율 1반',7),(54313,'부울경','자율 1반',7),(54314,'부울경','자율 2반',7),(54315,'전체','전체',7),(54316,'구미','전체',7),(54317,'광주','전체',7),(54318,'서울','전체',7),(54319,'대전','전체',7),(54320,'부울경','전체',7),(54321,'구미','1반',8),(54322,'구미','1반',8),(54323,'구미','2반',8),(54324,'서울','1반',8),(54325,'서울','2반',8),(54326,'서울','3반',8),(54327,'서울','4반',8),(54328,'서울','5반',8),(54329,'대전','1반',8),(54330,'대전','2반',8),(54331,'광주','2반',8),(54332,'광주','1반',8),(54333,'부울경','1반',8),(54334,'부울경','2반',8),(54335,'전체','전체',8),(54336,'구미','전체',8),(54337,'광주','전체',8),(54338,'서울','전체',8),(54339,'대전','전체',8),(54340,'부울경','전체',8);
/*!40000 ALTER TABLE `tbl_classroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `write_dt` varchar(255) DEFAULT NULL,
  `board_id` bigint DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgjlkbaxyvqqrmxeoxh6pb4oj7` (`user_id`),
  KEY `FK80tem8fmxkohufulwfcwp090p` (`board_id`),
  KEY `FKdgvrrot3m9s6n7emeuht70o73` (`parent_id`),
  CONSTRAINT `FK80tem8fmxkohufulwfcwp090p` FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKdgvrrot3m9s6n7emeuht70o73` FOREIGN KEY (`parent_id`) REFERENCES `tbl_comment` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKgjlkbaxyvqqrmxeoxh6pb4oj7` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_comment`
--

LOCK TABLES `tbl_comment` WRITE;
/*!40000 ALTER TABLE `tbl_comment` DISABLE KEYS */;
INSERT INTO `tbl_comment` VALUES (713,'test','1652408888420',NULL,NULL,572),(714,'test','1652408972422',NULL,NULL,572),(716,'test','1652409034922',NULL,713,572),(720,'test','1652409132356',NULL,NULL,572),(722,'test','1652409143859',NULL,720,572),(724,'test','1652409274567',NULL,720,572),(725,'test','1652409282148',NULL,NULL,572),(741,'댓글2','1652410170721',NULL,NULL,768),(747,'대댓3','1652414879443',NULL,741,183),(750,'댓글3','1652415082579',NULL,NULL,125),(842,'11','1652420468105',NULL,NULL,572),(843,'22','1652420473642',NULL,NULL,572),(844,'ㄷㄷ11','1652420478821',NULL,842,572),(845,'ㄷㄷ22','1652420484551',NULL,842,572),(894,'리얼ㅋㅋㅋ 나도 동문회 진행자 뽑던데 지원해볼까','1652422947639',892,NULL,657),(941,'우리는 한번도 싸운 적 없음 ㅋㅋ','1652425637822',900,NULL,441),(942,'신기하네 회의할때 싸움 안나?','1652425653612',900,941,441),(944,'싸울 일이 거의 없는데 답답한 사람은 있다 ㅋㅋㅋㅋ','1652425696863',900,NULL,441),(945,'아침에 프로젝트 전 게임 같이해봐 확실히 즐겁더라','1652425716079',900,NULL,441),(947,'나나나나나나!! 언제갈래?','1652425730442',901,NULL,441),(948,'나도 델꼬가','1652425738166',901,NULL,441),(949,'결말 : 베네딕트 죽음 ㅅㄱ','1652425761509',901,NULL,441),(951,'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 진심 지각 무서워 ㄹㅇ','1652425774612',925,NULL,441),(952,'딱히 특이사항 없고 주말 잘 쉬래요','1652425791831',NULL,NULL,441),(954,'ㅇㅇ 지원 한번 해보지 왜!!?!','1652425809250',892,894,441),(955,'ㄹㅇ ㅋㅋㅋㅋ 좀 무리해서라도 모바일반 갈걸 아쉬움 ㅜ','1652425835411',892,NULL,441),(958,'ㅋㅋㅋㅋ 고맙긴 뭘! 넣어둬 넣어둬!!','1652425870982',926,NULL,441),(960,'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 너... 봤구나?','1652425888275',927,NULL,441),(961,'ㅋㅋㅋㅋ 제목보고 깜짝 놀람.. ','1652425899539',928,NULL,441),(968,'4500, 난 만족함','1652426070055',929,NULL,441),(969,'월 200 받는데 이직하려고.','1652426083744',929,NULL,441),(972,'건의해봐 ','1652426104345',931,NULL,441),(973,'나도ㅜ, 연봉 상승률도 낮음..','1652426131881',932,NULL,441),(974,'스터디 게시판에 CS 구하는거 너야??? ','1652426162989',922,NULL,441),(978,'얼마에요???','1652426185690',902,NULL,441),(983,'ㅁㅊ 나주라!!','1652427474286',907,NULL,441),(984,'대박 나두 같이 마시자!!','1652427482345',907,NULL,441),(985,'선제 ㄱ','1652427489795',937,NULL,441),(987,'어디서 거래해요?','1652427507270',902,NULL,441),(988,'팔렸나요?','1652427520369',939,NULL,441),(1014,'1','1652614722673',NULL,NULL,177),(1015,'2','1652614730527',NULL,NULL,177),(1016,'3','1652614734160',NULL,NULL,177),(1017,'1','1652614738953',NULL,1014,572),(1018,'2','1652614744588',NULL,1014,177),(1019,'3','1652614750099',NULL,1014,177),(1028,'fcm test','1652626325915',NULL,NULL,177),(1030,'fcm test','1652626384195',NULL,1028,572),(1070,'2','1652667436516',NULL,NULL,572),(1073,'jiwoo','1652667666509',NULL,NULL,177),(1074,'dd','1652667677246',NULL,1073,572),(1203,'뭐지 셀프축하인가?','1652680317578',956,NULL,572),(1242,'no photo','1652690177463',1094,NULL,507),(1259,'넘 귀여워욬ㅋㅋㅋㅋ','1652766534093',1247,NULL,53),(1263,'ㅋㅋㅋㅋㅋㅋ','1652766594347',925,NULL,441),(1268,'에헷. ㅎ','1652766605182',1247,NULL,441),(1269,'ㄱㅅㄱㅅ','1652766652483',NULL,NULL,441),(1277,'ㅠ','1652768965394',956,NULL,441),(1280,'플러터,,?','1652769124469',920,NULL,177),(1283,'ㅇ','1652771295976',1247,NULL,572),(1289,'또롱이 사진 어디갔어요!??! 이름만 들어도 너무 귀여울 것 같아용','1652833425615',1094,NULL,177),(1291,'너 아주 나쁜 아이구나..?','1652833464370',901,949,177);
/*!40000 ALTER TABLE `tbl_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_consultant`
--

DROP TABLE IF EXISTS `tbl_consultant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_consultant` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjjchx6v4pxf9srxapfrlxpfkg` (`user_id`),
  CONSTRAINT `FKjjchx6v4pxf9srxapfrlxpfkg` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_consultant`
--

LOCK TABLES `tbl_consultant` WRITE;
/*!40000 ALTER TABLE `tbl_consultant` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_consultant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_like`
--

DROP TABLE IF EXISTS `tbl_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_like` (
  `id` bigint NOT NULL,
  `is_liked` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_like`
--

LOCK TABLES `tbl_like` WRITE;
/*!40000 ALTER TABLE `tbl_like` DISABLE KEYS */;
INSERT INTO `tbl_like` VALUES (1,1);
/*!40000 ALTER TABLE `tbl_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_like_management`
--

DROP TABLE IF EXISTS `tbl_like_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_like_management` (
  `id` bigint NOT NULL,
  `board_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhceh39tc8ha64y24bd5vnrmrs` (`board_id`),
  KEY `FKldsx6nbpdupe3o9dqowh02rbb` (`user_id`),
  CONSTRAINT `FKhceh39tc8ha64y24bd5vnrmrs` FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`id`),
  CONSTRAINT `FKldsx6nbpdupe3o9dqowh02rbb` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_like_management`
--

LOCK TABLES `tbl_like_management` WRITE;
/*!40000 ALTER TABLE `tbl_like_management` DISABLE KEYS */;
INSERT INTO `tbl_like_management` VALUES (1,NULL,140),(375,NULL,177),(399,NULL,177),(408,NULL,402),(580,NULL,177),(590,NULL,139),(591,NULL,140),(592,NULL,171),(593,NULL,174),(594,NULL,165),(595,NULL,156),(596,NULL,162),(597,NULL,183),(598,563,183),(599,563,125),(600,563,195),(601,563,93),(602,563,9),(603,563,441),(605,563,402),(606,563,174),(607,563,140),(608,563,139),(897,NULL,657),(898,892,657),(940,900,441),(946,901,441),(950,925,441),(953,NULL,441),(959,926,441),(962,927,441),(963,928,441),(965,935,441),(966,934,441),(967,920,441),(970,929,441),(971,931,441),(975,922,441),(976,921,441),(977,902,441),(980,956,441),(982,907,441),(986,939,441),(993,669,441),(994,930,441),(995,932,441),(996,672,441),(999,563,572),(1279,920,177),(1292,926,177),(1293,563,177),(1294,936,177),(1295,934,177);
/*!40000 ALTER TABLE `tbl_like_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_manager`
--

DROP TABLE IF EXISTS `tbl_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_manager` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `auth` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK441xxqq9fhaowk66l3xn3afor` (`user_id`),
  CONSTRAINT `FK441xxqq9fhaowk66l3xn3afor` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_manager`
--

LOCK TABLES `tbl_manager` WRITE;
/*!40000 ALTER TABLE `tbl_manager` DISABLE KEYS */;
INSERT INTO `tbl_manager` VALUES (157,156,2),(163,162,1),(166,165,1),(172,171,1),(175,174,2),(184,183,1),(356,355,2),(526,507,1),(533,532,1),(550,549,2),(573,572,2),(765,764,2),(769,768,1);
/*!40000 ALTER TABLE `tbl_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_message`
--

DROP TABLE IF EXISTS `tbl_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_message` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `send_dt` varchar(255) DEFAULT NULL,
  `u_from_id` bigint DEFAULT NULL,
  `u_to_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgwoqjmgxyrvi7dq4y300aviq4` (`u_from_id`),
  KEY `FK613qhrl5l54im36bcto0s58b5` (`u_to_id`),
  CONSTRAINT `FK613qhrl5l54im36bcto0s58b5` FOREIGN KEY (`u_to_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKgwoqjmgxyrvi7dq4y300aviq4` FOREIGN KEY (`u_from_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_message`
--

LOCK TABLES `tbl_message` WRITE;
/*!40000 ALTER TABLE `tbl_message` DISABLE KEYS */;
INSERT INTO `tbl_message` VALUES (362,'[스터디 337] \'study Title’에 지원하였습니다.','2022-05-03 05:34:45.341995',177,195),(628,'[스터디 626] \'TeamBuilding ’에 지원하였습니다.','2022-05-12 07:19:44.251216',177,195),(630,'[스터디 626] \'TeamBuilding ’의 지원을 수락하였습니다.','2022-05-12 07:20:18.727388',195,177),(793,'[스터디 759] \'HELLO ~ :)’에 지원하였습니다.','2022-05-13 04:59:56.533002',761,195),(794,'[스터디 759] \'HELLO ~ :)’에 지원하였습니다.','2022-05-13 05:00:23.794537',177,195),(795,'[스터디 759] \'HELLO ~ :)’에 지원하였습니다.','2022-05-13 05:01:19.050069',761,195),(796,'[스터디 759] \'HELLO ~ :)’에 지원하였습니다.','2022-05-13 05:01:33.867662',177,195),(797,'[스터디 759] \'HELLO ~ :)’에 지원하였습니다.','2022-05-13 05:03:20.502517',761,195),(800,'[스터디 798] \'welcome SSafy 2 Project’에 지원하였습니다.','2022-05-13 05:05:14.759051',761,195),(808,'[스터디 798] \'welcome SSafy 2 Project’에 지원하였습니다.','2022-05-13 05:08:46.887318',761,195),(811,'[스터디 809] \'dfsf sdfsdfs’에 지원하였습니다.','2022-05-13 05:12:17.171812',195,195),(827,'[스터디 821] \'building’에 지원하였습니다.','2022-05-13 05:20:36.191618',177,195),(830,'[스터디 821] \'building’의 지원을 수락하였습니다.','2022-05-13 05:23:22.607793',195,177),(851,'[스터디 821] \'building’에 지원하였습니다.','2022-05-13 05:43:54.292915',657,195),(852,'[스터디 821] \'building’에 지원하였습니다.','2022-05-13 05:43:54.347024',657,195),(990,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-13 07:39:01.364064',441,195),(991,'[스터디 436] \'Kotlin 같이 공부할 스터디원 모집’에 지원하였습니다.','2022-05-13 07:39:08.516403',441,402),(992,'[스터디 923] \'프로젝트 스터디원 모집중’에 지원하였습니다.','2022-05-13 07:39:13.259626',441,657),(1008,'ㅇㅇ','2022-05-15 10:44:00.851829',177,572),(1024,'fcm test','2022-05-15 14:31:57.170746',572,1022),(1025,'fcm test','2022-05-15 14:33:05.964',572,1022),(1033,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 00:33:33.665724',177,195),(1034,'[스터디 834] \'this is photoTest’에 지원하였습니다.','2022-05-16 00:33:56.729332',177,195),(1037,'[스터디 834] \'this is photoTest’의 지원을 수락하였습니다.','2022-05-16 00:35:57.943624',195,177),(1039,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’의 지원을 수락하였습니다.','2022-05-16 00:36:13.081312',195,177),(1048,'[스터디 631] \'TeamBuilding’에 지원하였습니다.','2022-05-16 02:09:10.279313',441,195),(1050,'[스터디 631] \'TeamBuilding’의 지원을 수락하였습니다.','2022-05-16 02:09:20.367827',195,441),(1051,'[스터디 631] \'TeamBuilding’에 지원하였습니다.','2022-05-16 02:12:07.058444',441,195),(1052,'[스터디 735] \'this is title for you’에 지원하였습니다.','2022-05-16 02:12:26.358811',441,195),(1054,'[스터디 735] \'this is title for you’의 지원을 수락하였습니다.','2022-05-16 02:12:34.011165',195,441),(1055,'[스터디 735] \'this is title for you’에 지원하였습니다.','2022-05-16 02:12:43.389842',441,195),(1056,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:13:26.073372',441,195),(1057,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:13:26.228163',441,195),(1058,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:43.512421',441,195),(1059,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:43.666487',441,195),(1060,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:45.096166',441,195),(1061,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:45.238833',441,195),(1062,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:46.772988',441,195),(1063,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:46.877525',441,195),(1064,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:48.344834',441,195),(1065,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’에 지원하였습니다.','2022-05-16 02:14:48.632706',441,195),(1067,'[스터디 821] \'building’의 지원을 수락하였습니다.','2022-05-16 02:16:05.494989',195,657),(1069,'[스터디 339] \'같이 오프라인으로 CS 준비하실분?’의 지원을 수락하였습니다.','2022-05-16 02:16:59.553161',195,441),(1077,'[스터디 1075] \'dfsd fsdfa’에 지원하였습니다.','2022-05-16 02:24:01.607549',441,195),(1079,'[스터디 1075] \'dfsd fsdfa’의 지원을 수락하였습니다.','2022-05-16 02:24:12.035615',195,441),(1260,'ㅎㅇㅎㅇ','2022-05-17 05:49:37.957595',441,53),(1262,'저희 형배 한 귀여움하져?','2022-05-17 05:49:53.899996',177,53),(1265,'hello~~~~~~~~~~~~','2022-05-17 05:49:56.67124',507,53);
/*!40000 ALTER TABLE `tbl_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_notice`
--

DROP TABLE IF EXISTS `tbl_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_notice` (
  `id` bigint NOT NULL,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `write_dt` datetime(6) DEFAULT NULL,
  `classroom_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8layons8j1ieoe1keqw8q4loq` (`user_id`),
  KEY `FK8ygf884nwyai1sl2kf13q8rhb` (`classroom_id`),
  KEY `FKenfurnrg6oviwy249vovobygt` (`type_id`),
  CONSTRAINT `FK8layons8j1ieoe1keqw8q4loq` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FK8ygf884nwyai1sl2kf13q8rhb` FOREIGN KEY (`classroom_id`) REFERENCES `tbl_classroom` (`id`),
  CONSTRAINT `FKenfurnrg6oviwy249vovobygt` FOREIGN KEY (`type_id`) REFERENCES `tbl_notice_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_notice`
--

LOCK TABLES `tbl_notice` WRITE;
/*!40000 ALTER TABLE `tbl_notice` DISABLE KEYS */;
INSERT INTO `tbl_notice` VALUES (870,'라이브방송[자율 프로젝트 - 코드 리팩토링]이 곧 시작됩니다!!\n에듀싸피를 통해 접속해주세요','','','라이브방송 접속 안내','2022-05-13 06:09:24.425706',8,6,549),(873,'라이브 미접속하신 분들은 서둘러 입장바랍니다!','','','라이브방송 안내','2022-05-13 06:10:19.502263',8,6,764),(874,'오전 미팅 5분 후에 시작하겠습니다.','','','오전 미팅','2022-05-13 06:11:30.771585',8,6,768),(875,'13시 팀장 미팅입니다.','','','팀장 미팅','2022-05-13 06:12:19.336130',8,6,768),(881,'4월 부터 \'아이들과미래재단\'으로 지원금 지급 담당이 변경이 됩니다. (교육지원금 서명절차는 변경X)\n교육지원금 지급과 직결되어 있기 때문에 에듀싸피 공지사항 확인하여 관련서류를 필수 등록하여주시기 바랍니다!','','0017932068','[필독!]','2022-05-13 06:13:25.833311',8,6,764),(886,'5시 종례 미팅입니다.','','','종례 미팅','2022-05-13 06:14:08.767477',8,6,768),(887,'- 프로젝트 산출물 등록: 5월 20일(금) 12시 이전까지 -최종평가 : 5월 20일(금) 13시~17시 *평가연장불가  -포트폴리오위크 : 5월 23(월)~30일(월) -UCC 산출물 등록 : 5월 24일(화) 24시 이전까지 -본선발표회 : 5월 25일(수)~26일(목), webex -결선발표회 : 5월 31일(화), 서울캠퍼스(역삼) 상기 일정은 코로나19 상황 등에 따라 변동 가능성 있습니다. ','','','5월 학사일정 [리마인드]합니다.','2022-05-13 09:41:21.154212',272,1,NULL),(889,'여러분들의 프로젝트를 돋보이기 위한 ?슬로건 제작 이벤트?가 진행됩니다.\n우리반이나 우리팀을 대표할 수 있는 각오나 열정이 돋보이는 슬로건을 제작해주세요. 우수 슬로건을 선정하여 네이버페이 1만원권을 선물?로 드립니다.','','0017932069','우리들의 슬로건 이벤트','2022-05-13 06:15:33.435354',8,6,764),(890,'아래 내용 읽어보시고 활발한 이용 부탁드립니다~~!! 다이렉트로 찐 현직자분들의 진솔한 답변을 받아볼 수 있으니 평소 궁금했던 다양한 유형의 질문들 게시판에 업로드해주세요!','','0017932070','[삼성임직원멘토링 게시판 안내?]','2022-05-13 06:17:10.808212',8,6,549),(893,'안녕하세요. SSAFY 사무국입니다. \n금일 코로나 확진자 수가 17만명을 넘어서고 있습니다. \n그간 코로나 확진시에는 입원치료나 격리치료를 하였으나 최근 확진자가 급증함에따라 방역지침이 변경되어 재택치료를 하는 경우가 많아져서 SSAFY 확진자 출결 기준을 재공지 합니다.\n□ 코로나19 출결 기준\n - 기존 : 1주일 입원/격리치료 기간 사유결석, 완치 후 자가격리기간에는 온라인 수업 참여 가능\n - 변경 : 1주일 입원/재택치료 기간 사유결석(무증상자 포함)\n - 제출 서류 : 격리 기간(시작일, 종료일)을 확인할 수 있는 증빙 서류(예: 격리 통보서) \n   ※ 코로나 격리해제확인서 보건소 이메일 요청 가능\n          궁금하신 점은 담당 교육프로에게 문의 부탁드립니다.\n - 적용 시점 : 2022년 2월 24일 부터 \n\n최근, SSAFY 교육생 중에서도 확진자가 매일 발생하고 있습니다. \nSSAFY는 교육생 전체의 안전을 최우선으로 생각하며, 코로나 상황에서도 교육과 취업지원의 공백이 없도록 여러분을 지원할 것입니다. \n개인의 건강과 원활한 과정 운영을 위해서 관련 가이드를 잘 준수해주실 것을 당부드립니다.감사합니다.','','','SSAFY 코로나19 출결기준 안내','2022-02-23 18:42:21.154212',272,1,NULL),(894,'안녕하세요, SSAFY 사무국입니다.\n\n\nSSAFY가 사회공헌 기관인 ‘아이들과미래재단’과 협력하게 되어,\n\n향후 교육지원금 지급 관련해서는 ‘아이들과미래재단’에서 담당할 예정입니다.\n\n\n이에, ‘아이들과미래재단’ 주관으로 교육지원금 관련 개인정보활용동의 후 증빙 재등록 등의\n\n\n후속 절차가 진행 될 예정임을 안내드립니다. \n\n\n\n□ 변경 내용\n\n   - 시  점 : 4월 교육지원금부터 적용 (※5.16(월) 지급)\n\n   - 내  용 : 교육생 통장 입금자명은 ‘삼성SSAFY’로 동일하나\n\n                   ‘아이들과미래재단’에서 교육지원금 지급 대행\n\n\n □ 관련서류 재등록 진행\n   - 서  류 : 아이들과미래재단 개인정보활용동의서, 통장 사본, 신분증 사본\n\n   - 일  정 : ~ 5.9 (월)\n\n   - 방  법 : 아래 URL 클릭 후 재등록 진행 (모바일/PC 가능)\n   \n※URL : https://app.modusign.co.kr/link/06028820-c6cc-11ec-896b-194c78b33019/authentication\n\n□ 문의 : 담당 프로','','','교육지원금 변경 안내','2022-04-29 14:14:21.154212',272,3,NULL),(895,'열정 hotsix!\n안녕하세요 6기 교육생 여러분! 실습 코치 강용수, 손현주 입니다.\n저번주 라이브 요약본 참여가 저조 :loopy-tears: 하여 한. 번. 더. 공지합니다!!!\n\n유튜브 라이브강의 시청 후 요약본을 학습 채널에 올려주시면, 매주 우수 요약본을 선정해서 기프티콘을 드리고 있습니다!\n어떤 대단한 요약본을 기대하는 것이 아닙니다..! 강의를 듣고 여러분들만의 형식으로 자유롭게 정리해주시면 됩니다!\n저희가 이렇게 기프티콘으로 유도를 하고 있지만 강의 내용을 자기 것으로 만들 수 있다는 점이 가장 중요합니다!!\n방송을 눈으로만 보는게 아니라 스스로 정리하는 게 더 기억에 오래 남을 수 있고, 그래야 비로소 자기 것이 될테니까요!\n\n분명히 여러분들에게 도움 될만한 내용들이니 열정 hotsix :fire_parrot: 답게 많은 참여 부탁드립니다!! ','','','라이브강의 요약본 작성 참여 재안내','2022-05-10 08:49:21.154212',272,1,NULL),(896,'나의 서비스 매력발산타임\n여러분들의 마지막 자율 프로젝트를 장식할 UCC경진대회가 진행됩니다.\n열정, 끈기를 갈아넣은 여러분들의 서비스를 뽐낼 특별한 기회! \n\n7주동안 여러분들의 노력이 꾹꾹 담긴 서비스!\n어떻게 해야 많은 사람들이 내 서비스를 많이 찾아줄까 많이 고민되시죠?\n\n내가 만든 서비스를 가장 잘 표현할 수 있는 UCC영상으로\n여러분의 개발물을 홍보해보세요!\n\n여러분들의 통통튀는 아이디어 가득 담긴 멋진 영상 기대해봅니다!\n\n - 기한 : 5/24(화) 23:59까지\n - 평가 : 기획력, 완성도, 창의성\n - 기획력 : 프로젝트 주제와 스토리라인의 적합성\n - 완성도 : 활용한 영상과 음성의 저작권 이슈 여부, 영상 길이의 적절성 (1분 30초 이상)\n - 창의성 : 작품 내용의 참신성과 독창성\n* 유의사항\n서비스에 대한 소개와 더불어 시연은 필수!\n내 서비스만의 특징을 살린 스토리/컨셉으로 표현\nSSAFY인들의 건강을 위해 영상촬영시 코로나 예방수칙 준수\n\np.s. 영상제작 꿀팁이나 제도적으로 궁금한 사항이 있다면 언제든 문의주세요','','','[자율PJT UCC경진대회 안내]','2022-05-12 13:15:21.154212',272,2,NULL),(1001,' 이벤트 기간: 4/25 (월) ~ 5/16 (월) *당첨자 발표: 5월','','0017932088','8기 친구추천 교육생이벤트!!','2022-05-13 08:38:30.505768',272,1,549),(1254,'9시 라이브방송[자율프로젝트 - 알아두면 쓸모 있는 데이터베이스 객체]이 곧 시작됩니다!!\n에듀싸피를 통해 접속해주세요','','','9시 라이브방송 접속 안내','2022-05-17 02:10:22.850157',8,6,572),(1296,'dsfsdfsdfdsfsdfsdf','','','dfsfdsdfsdfsdfsd','2022-05-18 01:49:13.101537',273,6,507);
/*!40000 ALTER TABLE `tbl_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_notice_type`
--

DROP TABLE IF EXISTS `tbl_notice_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_notice_type` (
  `id` bigint NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_notice_type`
--

LOCK TABLES `tbl_notice_type` WRITE;
/*!40000 ALTER TABLE `tbl_notice_type` DISABLE KEYS */;
INSERT INTO `tbl_notice_type` VALUES (1,'학습'),(2,'평가'),(3,'운영'),(4,'사이트'),(5,'기타'),(6,'반별');
/*!40000 ALTER TABLE `tbl_notice_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_notification`
--

DROP TABLE IF EXISTS `tbl_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_notification` (
  `id` bigint NOT NULL,
  `content` varchar(250) DEFAULT NULL,
  `date_time` varchar(25) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17xlvi4d2o1r18carkq5kmd3c` (`user_id`),
  CONSTRAINT `FK17xlvi4d2o1r18carkq5kmd3c` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_notification`
--

LOCK TABLES `tbl_notification` WRITE;
/*!40000 ALTER TABLE `tbl_notification` DISABLE KEYS */;
INSERT INTO `tbl_notification` VALUES (589,'9시에 유튜브 라이브방송 예정','','라이브방송 안내',1,549),(604,'사용자가 쓴 댓글에 대댓글이 달렸습니다.','','댓글 알림',2,549),(609,'쪽지가 수신되었습니다.','','쪽지 알림',2,572),(610,'현대백화점 IT개발 운영(서버, 안드로이드)','','현대백화점 그룹',3,572),(620,'쪽지가 수신되었습니다.','','쪽지 알림',2,549),(1020,'멘토 스토리에 신규 게시물이 등록되었습니다.','1652619321827','멘토 스토리 등록 안내',1,177),(1023,'SSAFY 교육생 인증이 완료되었습니다.\n이제부터 withSSAFY를 이용하실 수 있습니다.','1652620818763','교육생 인증 완료',2,1022),(1026,'쪽지가 수신되었습니다.','1652625187254','쪽지 알림',2,1022),(1027,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652626325709','댓글 알림',2,177),(1029,'사용자가 쓴 댓글에 대댓글이 달렸습니다.','1652626384008','댓글 알림',2,177),(1241,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652690177426','댓글 알림',2,53),(1250,'응용SW엔지니어링, [SSAFY 특별전형] PG 프론트엔드 개발 및 운영','1652747122803','섹타나인(SPC네트웍스)',3,53),(1251,'응용SW엔지니어링, [SSAFY 특별전형] PG 프론트엔드 개발 및 운영','1652747123170','섹타나인(SPC네트웍스)',3,177),(1253,'응용SW엔지니어링, [SSAFY 특별전형] PG 프론트엔드 개발 및 운영','1652747123889','섹타나인(SPC네트웍스)',3,572),(1257,'[ㅇ] \n게시글에 대해 접수한 신고 내역이 반려되었습니다.','1652766516200','신고 접수 처리',2,177),(1258,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652766534075','댓글 알림',2,177),(1261,'쪽지가 수신되었습니다.','1652766578420','쪽지 알림',2,53),(1264,'쪽지가 수신되었습니다.','1652766594378','쪽지 알림',2,53),(1266,'쪽지가 수신되었습니다.','1652766597014','쪽지 알림',2,53),(1267,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652766605162','댓글 알림',2,177),(1270,'[오늘 6기 종례 미팅] 접수하신 신고 내역에 대해 해당 게시글은 처리대상 게시글로 판단되어 삭제 처리 되었습니다.','1652766691897','신고 접수 처리',2,177),(1271,'[댓글2] \n댓글에 대해 접수한 신고 내역이 반려되었습니다.','1652766698316','신고 접수 처리',2,177),(1276,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652768965377','댓글 알림',2,441),(1282,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652771295954','댓글 알림',2,177),(1288,'사용자가 쓴 게시글에 댓글이 달렸습니다.','1652833425587','댓글 알림',2,53),(1290,'사용자가 쓴 댓글에 대댓글이 달렸습니다.','1652833464352','댓글 알림',2,441);
/*!40000 ALTER TABLE `tbl_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_pro`
--

DROP TABLE IF EXISTS `tbl_pro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_pro` (
  `id` bigint NOT NULL,
  `classroom_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf4e72n55n868th14aceaw5l8m` (`classroom_id`),
  CONSTRAINT `FKf4e72n55n868th14aceaw5l8m` FOREIGN KEY (`classroom_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_pro`
--

LOCK TABLES `tbl_pro` WRITE;
/*!40000 ALTER TABLE `tbl_pro` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_pro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_recruit`
--

DROP TABLE IF EXISTS `tbl_recruit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_recruit` (
  `id` bigint NOT NULL,
  `career` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `education` varchar(50) DEFAULT NULL,
  `employ_type` varchar(50) DEFAULT NULL,
  `end_date` varchar(10) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `preference_description` varchar(500) DEFAULT NULL,
  `salary` varchar(50) DEFAULT NULL,
  `start_date` varchar(10) DEFAULT NULL,
  `task_description` varchar(500) DEFAULT NULL,
  `welfare` varchar(500) DEFAULT NULL,
  `working_hours` varchar(100) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKid2e3oy3mprs13xnaqgijted8` (`user_id`),
  CONSTRAINT `FKid2e3oy3mprs13xnaqgijted8` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_recruit`
--

LOCK TABLES `tbl_recruit` WRITE;
/*!40000 ALTER TABLE `tbl_recruit` DISABLE KEYS */;
INSERT INTO `tbl_recruit` VALUES (858,'신입','신한DS','학사이상(예정자 포함)','정규직','2022-05-31','응용SW엔지니어링','서울시 서울 중구 남대문로 10길 29','정보처리기사 자격증 소지자','회사내규에 따름','2022-05-17','-금융시스템 개발/운영\n-그룹웨어 시스템 개발/운영\n-금융/비금융 모바일 개발/운영','-자녀학자금,의료비,복지포인트\n-경조사 지원금 및 물품 지급\n-연차 외 다양한 휴가 운영\n-자격증 취득비용 지원\n-스마트 전자도서관 운영 등','09:00~18:00',549,'0017932120'),(908,'신입','하나금융티아이','학사이상','정규직','2022-06-02','시스템SW엔지니어링','인천시 서구 청라3동833-5번지','SSAFY 전형','회사내규에 따름','2022-05-17','[Coding Track] 금융 IT서비스 개발/운영\n-금융IT 서비스 개발/운영\n(은행, 증권, 카드, 보험, 캐피탈 등)\n\n[Mobile Track] 모바일 개발/운영\n-금융IT 모바일 개발/운영\n(은행, 증권, 카드, 보험, 캐피탈 등)','-우수사원시상식, 워크샵, 플레이샵\n-해외연수지원, 멘토링제도, 외국어교육지원\n-경조휴가제\n-산전 후 휴가, 육아휴직, 보건휴가, 건강검진\n-각종 경조사 지원, 자녀학자금, 장기근속자 포상\n우수사원포상, 퇴직금, 성과급 등','주5일(월~금)',549,'0017932123'),(933,'신입','쏘카','관련학과 기졸업자','정규직','2022-06-13','웹 프론트엔드 개발자','서울시 성동구 왕십리로 83-21','SSAFY 전형','회사내규에 따름','2022-05-10','[담당하시게 될 업무를 소개합니다.]\n-웹 프론트엔드 기술(React.js, Next.js, Vue.js 등)을 이용 프로덕트를 운영하고 새로 만들어나가는 일','-재택근무제, 휴가는 셀프 결재\n-쏘카 할인 무제한, 타다 할인 무제한\n-서울숲에서의 산책 시간\n-최신형 맥북 + 모니터 2대\n-건강 지원: 매년 건강검진 지원\n-거주 지원: 전월세 대출 이자 지원\n-경조사 지원: 경조사비 & 휴가 지원\n-임신 지원: 단축 근로, 휴가 지원','시차 출퇴근제 운영',549,'0017932124'),(938,'신입','잡코리아','학사이상','정규직','2022-06-09','빅데이터 플랫폼 구축','서울시 서초구 서초대로 74길 14','SSAFY 전형','4000만원 이상','2022-05-24','[업무내용]\n빅데이터 플랫폼을 활용한 대용량 데이터 수집, 저장, 분석, 파이프라인 개발/운영\nML/DW를 위한 LakeHouse 개발/운영\n효율적인 데이터 처리와 플랫폼 개선을 위한 아이디어 설계 및 구현','4대보험, 선택적 근로제 운영, 재택근무 시행, 복지포인트, 퇴직연금, 각종 경조금 지원, 자녀 양육비 지원, 자녀 학자금 지원, 직원 대출 제도 운영, 장기 근속자 포상 제도, 교육비 지원, 야근수당, 휴일수당, 야간교통비 지급, 기념선물 지급, 직무능력향상교육, 리더쉽강화교육, 휴게실, 카페테리아 운영, 도서구입비 지원, 건강검진 본인 외 가족 1인 지원,  경조휴가, 심리상담, 운동보조금, 외국어 교육 지원 등','주 5일 (월~금)',549,'0017932125'),(957,'신입','(주)웅진','관련학과 기졸업자','정규직','2022-06-15','웹개발 / SAP BC','서울시 중구 청계천로24, 케이스퀘어 빌딩','정보처리기사 자격증 소지자','내규에 따름','2022-05-31','*웹 개발\n-그룹사 WEB시스템 개발 운영 및 단위 프로젝트 수행\n-대내외 고객사 WEB시스템 구축 프로젝트 수행\n\n*SAP BC (Basic COnsultant)\n-SAP System 운영 및 관리','-4대보험(국민연금, 건강보험, 고용보험, 산재보험)\n-연간복지포인트 지급\n-유연근무제\n-콘도예약서비스\n-웅진플레이도시 이용권(분기별)\n-근속년수에 따른 Refresh휴가\n-육아휴직, 배우자출산휴가, 가족돌봄휴가\n-각종 인센티브 제도\n-직원들의 건강증진을 위한 헬스케어 서비스\n-사내까페테리아\n-매년1회 본인건강검진 서비스 등','주5일 (월~금)',549,'0017932126'),(979,'신입','코멘토','학사이상','정규직','2022-06-16','웹서비스 Back-End 개발, Front-End 개발','서울시 종로구 종로51 24층 110호','SSAFY 전형','3120만원','2022-05-30','1. 웹 서비스 백엔드 개발 (php)\n[상세 업무]\n*코멘토 서비스의 신규 기능을 개발합니다.\n*코멘토 서비스의 기존 기능을 유지보수하고 성능을 개선합니다.\n*서비스 운영에 필요한 내부 툴을 개발합니다.\n\n2. 웹 서비스 프론트엔드 개발\n*신규 기능 개발\n-다양한 직무의 구성원들과 협업하여 코멘토의 새로운 기능을 함께 개발합니다.\n*실험\n-A/B Test, 고객 행동 분석 등을 통해 코멘토의 고객들을 학습하며, 서비스의 성장에 기여합니다.\n*기존 기능 유지보수\n-높은 수준의 서비스 만족도를 느낄 수 있도록 웹과 모바일웹앱의 서비스적, 기술적 품질을 향상합니다.','*신규 직원과 대표의 1 on 1 미팅: 신규 직원이 단순히 업무에만 적응하는 것이 아니라 올바르게 코멘토의 핵심 가치와 철학을 이해하고 가지고있는 꿈을 일치시키기 위해 초기 3개월 간 대표가 직접 미팅\n*4대보펌, 개인 장비, 점심식대, 야근식대, 점심시간 1시간 30분, 청년내일채움공제 등','주5일 (월~금)',549,'0017932127'),(1248,'신입','고영테크놀러지','학사이상','계약직','2022-05-31','응용SW엔지니어링','경기도 용인시 수지구 포은대로 59번길 18 고영테크놀러지 R&D센터','정보처리기사 자격증 소지자','월 220만원(정규직 전환 시 연봉 4180만원(성과금 별도))','2022-05-17','-  Windows 기반 클라이언트 어플리케이션 개발\n-  기존 Delphi / VB 개발 어플리케이션의 WPF기반 C# 리뉴얼 작업\n-  자사 검사 장비 (AOI/SPI/MOI/DPI) Software 개발 및 기능 개선','- 사내 헬스장 : 임직원 전용 헬스장 운영 / 운동복 및 수건, 사워실 등 완비\n- 사내 카페 운영 : 임직원 전용으로, 바리스타가 내려주는 원두커피 및 각종 음료 무료 제공\n- 임직원 휴게실 운영 : 남녀 구분하여 휴게실 설치\n- 기숙사 지원 : 수도권 외 지역 출신 직원에 대한 기숙사 지원\n- 의료비 지원 : 전 직원 개인 상해보험 가입\n- 건강검진 프로그램 : 2년마다 최고 수준으 ㅣ건강 검진 프로그램 시행\n- 경조 휴가 / 경조금 : 경조사 관련 휴가 및 경조금 지원\n- 임직원 자녀 양육비 지원 : 취학 전 자녀 보육비 지급\n- 기념일 축하 : 본인 및 배우자 생일 상품권 지급 / 결혼기념일 상품권 지급','주 5일 (월 ~ 금) 오전 9시 ~ 오후 6시',572,'0000000016'),(1249,'신입','섹타나인(SPC네트웍스)','학사이상','정규직','2022-05-22','응용SW엔지니어링, [SSAFY 특별전형] PG 프론트엔드 개발 및 운영','서울시 강남구 남부순환로355길 12','SSAFY 전형','회사 내규에 따름','2022-05-17','\n[JOB SUMMARY) 섹타나인은 고객지급결제 시스템과 SPC 그룹 IT 서비스 , 모바일 마케팅 사업을 총괄 운영하고 있습니다. 본 포지션은 섹타나인의 페이먼트사업부에서 결제시스템과 관련된 IT 개발 및 운영을 담당해주실 신입사원을 채용하는 건 입니다 이 채용공고는 SSAFY 교육생만 지원할 수 있습니다. \n[담당업무] PG 프론트엔드 개발 및 운영 - PG(payment Gateway)Front system 개발 및 운영 - 가맹점 기술지원 및 그룹/제휴사 소통, 협업','1. 복지포인트 제공 \n2. 임직원 통합 할인 \n3. 경조사 지원 및 포상 \n4. 건강검진 및 보험제도 \n5. 자녀 학자금 및 학위취득 지원 \n6. 휴가 및 직원 편의 지원','주 5일',572,'0000000017');
/*!40000 ALTER TABLE `tbl_recruit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_recruit_like_management`
--

DROP TABLE IF EXISTS `tbl_recruit_like_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_recruit_like_management` (
  `id` bigint NOT NULL,
  `recruit_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9svdtjsolmuus3qn6lifb1hpf` (`user_id`),
  KEY `tbl_recruit_like_management_ibfk_1` (`recruit_id`),
  CONSTRAINT `FK9glp8xvyfg2cius8f7ompdqay` FOREIGN KEY (`recruit_id`) REFERENCES `tbl_recruit` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK9svdtjsolmuus3qn6lifb1hpf` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `tbl_recruit_like_management_ibfk_1` FOREIGN KEY (`recruit_id`) REFERENCES `tbl_recruit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_recruit_like_management`
--

LOCK TABLES `tbl_recruit_like_management` WRITE;
/*!40000 ALTER TABLE `tbl_recruit_like_management` DISABLE KEYS */;
INSERT INTO `tbl_recruit_like_management` VALUES (1084,933,177),(1255,858,195),(1256,1248,195),(1286,933,53),(1287,957,53);
/*!40000 ALTER TABLE `tbl_recruit_like_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_report`
--

DROP TABLE IF EXISTS `tbl_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_report` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `board_id` bigint DEFAULT NULL,
  `comment_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `write_dt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ii79ia7rf9pbypbbntjfx9o8` (`comment_id`),
  KEY `FK4a72vbklhiydq6clrsx77m2w5` (`user_id`),
  KEY `FKae7jrsatr98104vg3gg9dijab` (`board_id`),
  CONSTRAINT `FK1ii79ia7rf9pbypbbntjfx9o8` FOREIGN KEY (`comment_id`) REFERENCES `tbl_comment` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK4a72vbklhiydq6clrsx77m2w5` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKae7jrsatr98104vg3gg9dijab` FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_report`
--

LOCK TABLES `tbl_report` WRITE;
/*!40000 ALTER TABLE `tbl_report` DISABLE KEYS */;
INSERT INTO `tbl_report` VALUES (1284,'this is not alright',925,NULL,507,'1652772182519'),(1285,'게시글 내용과 상관없는 댓글입니다.',NULL,1283,177,'1652772633050');
/*!40000 ALTER TABLE `tbl_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sb_comment`
--

DROP TABLE IF EXISTS `tbl_sb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_sb_comment` (
  `id` bigint NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `sb_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `write_dt` varchar(255) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgt03m3qq1q77rj8nxy2iemho2` (`user_id`),
  KEY `FKrfen8dwek7frp3udec3slqlrv` (`parent_id`),
  KEY `FK8hu0jwoh9igwba0wq376m9ph` (`sb_id`),
  CONSTRAINT `FK8hu0jwoh9igwba0wq376m9ph` FOREIGN KEY (`sb_id`) REFERENCES `tbl_study_board` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKgt03m3qq1q77rj8nxy2iemho2` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKrfen8dwek7frp3udec3slqlrv` FOREIGN KEY (`parent_id`) REFERENCES `tbl_sb_comment` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sb_comment`
--

LOCK TABLES `tbl_sb_comment` WRITE;
/*!40000 ALTER TABLE `tbl_sb_comment` DISABLE KEYS */;
INSERT INTO `tbl_sb_comment` VALUES (1005,'스터디 댓글1',1003,177,'1652611088367',NULL),(1007,'댓글1',1003,572,'1652611300890',NULL),(1011,'ㄷㄷ1-수정',1003,177,'1652612871745',1005),(1012,'ㄷㄷ2',1003,177,'1652613137170',1005),(1013,'ㄷㄷ3',1003,177,'1652613148782',1005),(1199,'1',1071,572,'1652679877521',NULL),(1281,'1',1272,572,'1652770513513',NULL);
/*!40000 ALTER TABLE `tbl_sb_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_schedule`
--

DROP TABLE IF EXISTS `tbl_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_schedule` (
  `id` bigint NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `memo` varchar(250) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `class_room_id` bigint DEFAULT NULL,
  `weeks` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKru86t7ptih3aabgna02jvwexa` (`user_id`),
  KEY `FK8cywu2deepmynouuqh9bl5jsa` (`class_room_id`),
  CONSTRAINT `FK8cywu2deepmynouuqh9bl5jsa` FOREIGN KEY (`class_room_id`) REFERENCES `tbl_classroom` (`id`),
  CONSTRAINT `FKru86t7ptih3aabgna02jvwexa` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_schedule`
--

LOCK TABLES `tbl_schedule` WRITE;
/*!40000 ALTER TABLE `tbl_schedule` DISABLE KEYS */;
INSERT INTO `tbl_schedule` VALUES (1166,'2022-05-02 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-02 10:00:00.000000','프로젝트진행',507,272,0),(1167,'2022-05-03 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-03 10:00:00.000000','프로젝트진행',507,272,0),(1168,'2022-05-04 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-04 10:00:00.000000','프로젝트진행',507,272,0),(1169,'2022-05-06 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-06 10:00:00.000000','프로젝트진행',507,272,0),(1170,'2022-05-09 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-09 10:00:00.000000','프로젝트진행',507,272,0),(1171,'2022-05-10 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-10 10:00:00.000000','프로젝트진행',507,272,0),(1172,'2022-05-11 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-11 10:00:00.000000','프로젝트진행',507,272,0),(1173,'2022-05-12 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-12 10:00:00.000000','프로젝트진행',507,272,0),(1174,'2022-05-13 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-13 10:00:00.000000','프로젝트진행',507,272,0),(1175,'2022-05-16 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-16 10:00:00.000000','프로젝트진행',507,272,0),(1176,'2022-05-17 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-17 10:00:00.000000','프로젝트진행',507,272,0),(1177,'2022-05-18 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-18 10:00:00.000000','프로젝트진행',507,272,0),(1178,'2022-05-19 18:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-19 10:00:00.000000','프로젝트진행',507,272,0),(1179,'2022-05-20 12:00:00.000000','자율 coding: 팀별 PJT 진행','2022-05-20 09:00:00.000000','프로젝트진행',507,272,0),(1180,'2022-05-11 10:00:00.000000','[LIVE] 캐싱의 개념과 적용','2022-05-11 10:00:00.000000','라이브방송',507,272,0),(1181,'2022-05-10 10:00:00.000000','[LIVE] 모바일 앱, 어떤 프레임워크를 써야할까?','2022-05-10 10:00:00.000000','라이브방송',507,272,0),(1182,'2022-05-20 18:00:00.000000','최종 평가','2022-05-20 09:00:00.000000','최종평가',507,272,0),(1183,'2022-05-20 18:00:00.000000','자율 프로젝트 마무리','2022-05-20 10:00:00.000000','팀별 PJT 마무리	',507,272,0),(1184,'2022-05-19 10:00:00.000000','[LIVE] 우수 포트폴리오 소개','2022-05-19 10:00:00.000000','라이브방송',507,272,0),(1185,'2022-05-03 10:00:00.000000','[LIVE] 정규표현식','2022-05-03 09:00:00.000000','라이브방송',507,272,0),(1186,'2022-05-04 10:00:00.000000','[LIVE] 코드 리펙토링','2022-05-04 09:00:00.000000','라이브방송',507,272,0),(1187,'2022-05-10 10:00:00.000000','[LIVE] 모바일 앱, 어떤 프레임워크를 사용해야할까?','2022-05-10 09:00:00.000000','라이브방송',507,272,0),(1188,'2022-05-11 10:00:00.000000','[LIVE] 대상의 개념과 적용','2022-05-11 09:00:00.000000','라이브방송',507,272,0),(1189,'2022-05-12 10:00:00.000000','[LIVE] 플러터로 앱 개발해보기','2022-05-12 09:00:00.000000','라이브방송',507,272,0),(1190,'2022-05-17 10:00:00.000000','[LIVE] 알아두면 쓸모 있는 데이터','2022-05-17 09:00:00.000000','라이브방송',507,272,0),(1191,'2022-05-18 10:00:00.000000','[LIVE] 프로젝트 발표 Tip','2022-05-18 09:00:00.000000','라이브방송',507,272,0),(1192,'2022-05-19 10:00:00.000000','[LIVE] 우수 포트폴리오 소개','2022-05-19 09:00:00.000000','라이브방송',507,272,0),(5000,'2022-05-09 10:00:00.000000','[화상_반]','2022-05-09 09:00:00.000000','오전미팅',507,8,19),(5001,'2022-05-09 12:00:00.000000','[자율]','2022-05-09 10:00:00.000000','프로젝트진행',507,8,19),(5002,'2022-05-09 13:00:00.000000','','2022-05-09 12:00:00.000000','중식',507,8,19),(5003,'2022-05-09 14:00:00.000000','[화상_팀장]','2022-05-09 13:00:00.000000','오후미팅',507,8,19),(5004,'2022-05-09 17:00:00.000000','[자율]','2022-05-09 14:00:00.000000','프로젝트진행',507,8,19),(5005,'2022-05-09 18:00:00.000000','[화상_반]','2022-05-09 17:00:00.000000','종료미팅',507,8,19),(5006,'2022-05-10 10:00:00.000000','[화상_반][Live방송]','2022-05-10 09:00:00.000000','오전미팅',507,8,19),(5007,'2022-05-10 12:00:00.000000','[자율]','2022-05-10 10:00:00.000000','프로젝트진행',507,8,19),(5008,'2022-05-10 13:00:00.000000','','2022-05-10 12:00:00.000000','중식',507,8,19),(5009,'2022-05-10 14:00:00.000000','[화상_팀장]','2022-05-10 13:00:00.000000','오후미팅',507,8,19),(5010,'2022-05-10 17:00:00.000000','[자율]','2022-05-10 14:00:00.000000','프로젝트진행',507,8,19),(5011,'2022-05-10 18:00:00.000000','[화상_반]','2022-05-10 17:00:00.000000','종료미팅',507,8,19),(5012,'2022-05-11 10:00:00.000000','[화상_반][Live방송]','2022-05-11 09:00:00.000000','오전미팅',507,8,19),(5013,'2022-05-11 12:00:00.000000','[자율]','2022-05-11 10:00:00.000000','프로젝트진행',507,8,19),(5014,'2022-05-11 13:00:00.000000','','2022-05-11 12:00:00.000000','중식',507,8,19),(5015,'2022-05-11 14:00:00.000000','[화상_팀장]','2022-05-11 13:00:00.000000','오후미팅',507,8,19),(5016,'2022-05-11 17:00:00.000000','[자율]','2022-05-11 14:00:00.000000','프로젝트진행',507,8,19),(5017,'2022-05-11 18:00:00.000000','[화상_반]','2022-05-11 17:00:00.000000','종료미팅',507,8,19),(5018,'2022-05-12 10:00:00.000000','[화상_반][Live방송]','2022-05-12 09:00:00.000000','오전미팅',507,8,19),(5019,'2022-05-12 12:00:00.000000','[자율]','2022-05-12 10:00:00.000000','프로젝트진행',507,8,19),(5020,'2022-05-12 13:00:00.000000','','2022-05-12 12:00:00.000000','중식',507,8,19),(5021,'2022-05-12 14:00:00.000000','[화상_팀장]','2022-05-12 13:00:00.000000','오후미팅',507,8,19),(5022,'2022-05-12 17:00:00.000000','[자율]','2022-05-12 14:00:00.000000','프로젝트진행',507,8,19),(5023,'2022-05-12 18:00:00.000000','[화상_반]','2022-05-12 17:00:00.000000','종료미팅',507,8,19),(5024,'2022-05-13 10:00:00.000000','[화상_반]','2022-05-13 09:00:00.000000','오전미팅',507,8,19),(5025,'2022-05-13 12:00:00.000000','[자율]','2022-05-13 10:00:00.000000','프로젝트진행',507,8,19),(5026,'2022-05-13 13:00:00.000000','','2022-05-13 12:00:00.000000','중식',507,8,19),(5027,'2022-05-13 14:00:00.000000','[화상_팀장]','2022-05-13 13:00:00.000000','오후미팅',507,8,19),(5028,'2022-05-13 17:00:00.000000','[자율]','2022-05-13 14:00:00.000000','프로젝트진행',507,8,19),(5029,'2022-05-13 18:00:00.000000','[화상_반]','2022-05-13 17:00:00.000000','SSAFY Day',507,8,19),(5030,'2022-05-02 10:00:00.000000','[화상_반]','2022-05-02 09:00:00.000000','오전미팅',507,8,18),(5031,'2022-05-02 12:00:00.000000','[자율]','2022-05-02 10:00:00.000000','프로젝트진행',507,8,18),(5032,'2022-05-02 13:00:00.000000','','2022-05-02 12:00:00.000000','중식',507,8,18),(5033,'2022-05-02 14:00:00.000000','[화상_팀장]','2022-05-02 13:00:00.000000','오후미팅',507,8,18),(5034,'2022-05-02 17:00:00.000000','[자율]','2022-05-02 14:00:00.000000','프로젝트진행',507,8,18),(5035,'2022-05-02 18:00:00.000000','[화상_반]','2022-05-02 17:00:00.000000','종료미팅',507,8,18),(5036,'2022-05-03 10:00:00.000000','[화상_반][Live방송]','2022-05-03 09:00:00.000000','오전미팅',507,8,18),(5037,'2022-05-03 12:00:00.000000','[자율]','2022-05-03 10:00:00.000000','프로젝트진행',507,8,18),(5038,'2022-05-03 13:00:00.000000','','2022-05-03 12:00:00.000000','중식',507,8,18),(5039,'2022-05-03 14:00:00.000000','[화상_팀장]','2022-05-03 13:00:00.000000','오후미팅',507,8,18),(5040,'2022-05-03 17:00:00.000000','[자율]','2022-05-03 14:00:00.000000','프로젝트진행',507,8,18),(5041,'2022-05-03 18:00:00.000000','[화상_반]','2022-05-03 17:00:00.000000','종료미팅',507,8,18),(5042,'2022-05-04 10:00:00.000000','[화상_반][Live방송]','2022-05-04 09:00:00.000000','오전미팅',507,8,18),(5043,'2022-05-04 12:00:00.000000','[자율]','2022-05-04 10:00:00.000000','프로젝트진행',507,8,18),(5044,'2022-05-04 13:00:00.000000','','2022-05-04 12:00:00.000000','중식',507,8,18),(5045,'2022-05-04 14:00:00.000000','[화상_팀장]','2022-05-04 13:00:00.000000','오후미팅',507,8,18),(5046,'2022-05-04 17:00:00.000000','[자율]','2022-05-04 14:00:00.000000','프로젝트진행',507,8,18),(5047,'2022-05-04 18:00:00.000000','[화상_반]','2022-05-04 17:00:00.000000','종료미팅',507,8,18),(5048,'2022-05-05 18:00:00.000000','','2022-05-05 09:00:00.000000','어린이날',507,8,18),(5049,'2022-05-06 10:00:00.000000','[화상_반]','2022-05-06 09:00:00.000000','오전미팅',507,8,18),(5050,'2022-05-06 12:00:00.000000','[자율]','2022-05-06 10:00:00.000000','프로젝트진행',507,8,18),(5051,'2022-05-06 13:00:00.000000','','2022-05-06 12:00:00.000000','중식',507,8,18),(5052,'2022-05-06 14:00:00.000000','[화상_팀장]','2022-05-06 13:00:00.000000','오후미팅',507,8,18),(5053,'2022-05-06 17:00:00.000000','[자율]','2022-05-06 14:00:00.000000','프로젝트진행',507,8,18),(5054,'2022-05-06 18:00:00.000000','[화상_반]','2022-05-06 17:00:00.000000','종료미팅',507,8,18),(5055,'2022-04-25 10:00:00.000000','[화상_반]','2022-04-25 09:00:00.000000','오전미팅',507,8,17),(5056,'2022-04-25 12:00:00.000000','[자율]','2022-04-25 10:00:00.000000','프로젝트진행',507,8,17),(5057,'2022-04-25 13:00:00.000000','','2022-04-25 12:00:00.000000','중식',507,8,17),(5058,'2022-04-25 14:00:00.000000','[화상_팀장]','2022-04-25 13:00:00.000000','오후미팅',507,8,17),(5059,'2022-04-25 17:00:00.000000','[자율]','2022-04-25 14:00:00.000000','프로젝트진행',507,8,17),(5060,'2022-04-25 18:00:00.000000','[화상_반]','2022-04-25 17:00:00.000000','종료미팅',507,8,17),(5061,'2022-04-26 10:00:00.000000','[화상_반][Live방송]','2022-04-26 09:00:00.000000','오전미팅',507,8,17),(5062,'2022-04-26 12:00:00.000000','[자율]','2022-04-26 10:00:00.000000','프로젝트진행',507,8,17),(5063,'2022-04-26 13:00:00.000000','','2022-04-26 12:00:00.000000','중식',507,8,17),(5064,'2022-04-26 14:00:00.000000','[화상_팀장]','2022-04-26 13:00:00.000000','오후미팅',507,8,17),(5065,'2022-04-26 17:00:00.000000','[자율]','2022-04-26 14:00:00.000000','프로젝트진행',507,8,17),(5066,'2022-04-26 18:00:00.000000','[화상_반]','2022-04-26 17:00:00.000000','종료미팅',507,8,17),(5067,'2022-04-27 10:00:00.000000','[화상_반][Live방송]','2022-04-27 09:00:00.000000','오전미팅',507,8,17),(5068,'2022-04-27 12:00:00.000000','[자율]','2022-04-27 10:00:00.000000','프로젝트진행',507,8,17),(5069,'2022-04-27 13:00:00.000000','','2022-04-27 12:00:00.000000','중식',507,8,17),(5070,'2022-04-27 14:00:00.000000','[화상_팀장]','2022-04-27 13:00:00.000000','오후미팅',507,8,17),(5071,'2022-04-27 17:00:00.000000','[자율]','2022-04-27 14:00:00.000000','프로젝트진행',507,8,17),(5072,'2022-04-27 18:00:00.000000','[화상_반]','2022-04-27 17:00:00.000000','종료미팅',507,8,17),(5073,'2022-04-28 10:00:00.000000','[화상_반][Live방송]','2022-04-28 09:00:00.000000','오전미팅',507,8,17),(5074,'2022-04-28 12:00:00.000000','[자율]','2022-04-28 10:00:00.000000','프로젝트진행',507,8,17),(5075,'2022-04-28 13:00:00.000000','','2022-04-28 12:00:00.000000','중식',507,8,17),(5076,'2022-04-28 14:00:00.000000','[화상_팀장]','2022-04-28 13:00:00.000000','오후미팅',507,8,17),(5077,'2022-04-28 17:00:00.000000','[자율]','2022-04-28 14:00:00.000000','프로젝트진행',507,8,17),(5078,'2022-04-28 18:00:00.000000','[화상_반]','2022-04-28 17:00:00.000000','종료미팅',507,8,17),(5079,'2022-04-29 10:00:00.000000','[화상_반]','2022-04-29 09:00:00.000000','오전미팅',507,8,17),(5080,'2022-04-29 12:00:00.000000','[자율]','2022-04-29 10:00:00.000000','프로젝트진행',507,8,17),(5081,'2022-04-29 13:00:00.000000','','2022-04-29 12:00:00.000000','중식',507,8,17),(5082,'2022-04-29 14:00:00.000000','[화상_팀장]','2022-04-29 13:00:00.000000','오후미팅',507,8,17),(5083,'2022-04-29 17:00:00.000000','','2022-04-29 14:00:00.000000','해피투게더\n SSAFY MeetUp',507,8,17),(5084,'2022-04-29 18:00:00.000000','[화상_반]','2022-04-29 17:00:00.000000','종료미팅',507,8,17);
/*!40000 ALTER TABLE `tbl_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_study_board`
--

DROP TABLE IF EXISTS `tbl_study_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_study_board` (
  `id` bigint NOT NULL,
  `area` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `is_outing` tinyint DEFAULT NULL,
  `photo_path` varchar(500) DEFAULT NULL,
  `sb_limit` int DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `write_datetime` varchar(500) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1p6na0tf1hp41tn54prw9pwnk` (`user_id`),
  CONSTRAINT `FK1p6na0tf1hp41tn54prw9pwnk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_study_board`
--

LOCK TABLES `tbl_study_board` WRITE;
/*!40000 ALTER TABLE `tbl_study_board` DISABLE KEYS */;
INSERT INTO `tbl_study_board` VALUES (436,'대구','프로그래밍','같이 코틀린 공부하면서 안드로이드 앱 개발 할 사람?',0,'',4,'Kotlin 같이 공부할 스터디원 모집','1651646540183',402,0),(821,'구미','웹 기술','tesfsd fsdf',-1,'',2,'building','1652419078725',195,1),(834,'구미','웹 디자인','photophotophotophoto',-1,'0017932067',6,'this is photoTest','1652419477912',195,1),(1003,'서울','프로그래밍','하이하이하이하잉ㅇㅇㅇㅇ',0,'',2,'하이하이','1652611076244',177,0),(1071,'서울','어학','zsddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\\\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nz',0,'0017932128',1,'test','1652667449049',441,0),(1272,'구미','인공지능음성','수상을 목표로 열정적으로 임하실 분 모집합니다 :)',-1,'',2,'백엔드 한분 구합니다.','1652768817254',53,1),(1274,'구미','인공지능영상','\n\n\n인공지능을 재밌게 공부하면서 즐겁게 프로젝트 진행했으면 좋겠습니다ㅎㅎ 많이 지원해주세요~!',-1,'',6,'인공지능 흥미있는 분들 환영합니다!','1652768921842',53,1);
/*!40000 ALTER TABLE `tbl_study_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_study_member`
--

DROP TABLE IF EXISTS `tbl_study_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_study_member` (
  `id` bigint NOT NULL,
  `sb_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9j1spw6xuai8rfrueneev0q4x` (`user_id`),
  KEY `FKmnqfowov1wy7oyfwwomi0s0j2` (`sb_id`),
  CONSTRAINT `FK9j1spw6xuai8rfrueneev0q4x` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKmnqfowov1wy7oyfwwomi0s0j2` FOREIGN KEY (`sb_id`) REFERENCES `tbl_study_board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_study_member`
--

LOCK TABLES `tbl_study_member` WRITE;
/*!40000 ALTER TABLE `tbl_study_member` DISABLE KEYS */;
INSERT INTO `tbl_study_member` VALUES (338,NULL,195),(340,NULL,195),(342,NULL,195),(437,436,402),(623,NULL,195),(625,NULL,195),(627,NULL,195),(629,NULL,177),(632,NULL,195),(727,NULL,657),(729,NULL,657),(731,NULL,657),(736,NULL,195),(760,NULL,195),(799,NULL,195),(810,NULL,195),(822,821,195),(829,821,177),(835,834,195),(924,NULL,657),(1004,1003,177),(1036,834,177),(1038,NULL,177),(1049,NULL,441),(1053,NULL,441),(1066,821,657),(1068,NULL,441),(1072,1071,441),(1076,NULL,195),(1078,NULL,441),(1194,NULL,53),(1196,NULL,53),(1198,NULL,53),(1206,NULL,195),(1208,NULL,195),(1210,NULL,195),(1212,NULL,195),(1214,NULL,195),(1216,NULL,195),(1218,NULL,195),(1220,NULL,53),(1222,NULL,53),(1224,NULL,53),(1226,NULL,195),(1228,NULL,195),(1230,NULL,53),(1232,NULL,195),(1234,NULL,53),(1236,NULL,53),(1238,NULL,195),(1240,NULL,195),(1273,1272,53),(1275,1274,53);
/*!40000 ALTER TABLE `tbl_study_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tb_comment`
--

DROP TABLE IF EXISTS `tbl_tb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_tb_comment` (
  `id` bigint NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `parent` int DEFAULT NULL,
  `tb_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2pa14kbr1qy4oi6o1uf1b1gxn` (`user_id`),
  KEY `FKl1iv42eop0o5xl116rmq2r302` (`tb_id`),
  CONSTRAINT `FK2pa14kbr1qy4oi6o1uf1b1gxn` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKl1iv42eop0o5xl116rmq2r302` FOREIGN KEY (`tb_id`) REFERENCES `tbl_team_building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tb_comment`
--

LOCK TABLES `tbl_tb_comment` WRITE;
/*!40000 ALTER TABLE `tbl_tb_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tb_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_team`
--

DROP TABLE IF EXISTS `tbl_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_team` (
  `id` bigint NOT NULL,
  `classification` int DEFAULT NULL,
  `max_limit` int DEFAULT NULL,
  `min_limit` int DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_team`
--

LOCK TABLES `tbl_team` WRITE;
/*!40000 ALTER TABLE `tbl_team` DISABLE KEYS */;
INSERT INTO `tbl_team` VALUES (678,2,6,3,NULL),(681,1,5,1,NULL),(682,0,5,1,NULL),(715,1,5,1,'-'),(719,0,3,1,'12'),(723,1,6,2,'-'),(758,0,6,1,'-'),(1035,1,6,1,'dd');
/*!40000 ALTER TABLE `tbl_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_team_building`
--

DROP TABLE IF EXISTS `tbl_team_building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_team_building` (
  `id` bigint NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `photo_path` varchar(500) DEFAULT NULL,
  `tb_limit` int DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKng29oa8670tlguiv42fj5awgu` (`user_id`),
  CONSTRAINT `FKng29oa8670tlguiv42fj5awgu` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_team_building`
--

LOCK TABLES `tbl_team_building` WRITE;
/*!40000 ALTER TABLE `tbl_team_building` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_team_building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_team_member`
--

DROP TABLE IF EXISTS `tbl_team_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_team_member` (
  `id` bigint NOT NULL,
  `position` int DEFAULT NULL,
  `tb_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5oprrecu3vvdl59j8l3a0kgkv` (`user_id`),
  KEY `FKgnrknvkjopi38op1xxx16uygt` (`tb_id`),
  CONSTRAINT `FK5oprrecu3vvdl59j8l3a0kgkv` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `FKgnrknvkjopi38op1xxx16uygt` FOREIGN KEY (`tb_id`) REFERENCES `tbl_team_building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_team_member`
--

LOCK TABLES `tbl_team_member` WRITE;
/*!40000 ALTER TABLE `tbl_team_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_team_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `id` bigint NOT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(25) DEFAULT NULL,
  `classroom_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hjtucs0khdiwxkbgwr3f1g9hq` (`user_id`),
  KEY `FKs8benq7dy9qc1loemco1y6xlg` (`classroom_id`),
  CONSTRAINT `FKs8benq7dy9qc1loemco1y6xlg` FOREIGN KEY (`classroom_id`) REFERENCES `tbl_classroom` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (9,NULL,'송경희','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',1,'0623650','song',15),(47,NULL,'ssongg','송겨희',1,'0623650','991144566s!',6),(53,'chZMWVaKSZ6xAIvKsu4bZr:APA91bEvKM-pSkjIoL-sPLRmGszc_af52PM5Zt3J-fc78Ixhyg-WYzN7G219NhNgqV0AfYceHD7Lf_cr1G6wIE-tAHy40KDp7Y_MTjQrcucDzA9SElqo1I3-ax28XEJwo3VUMFpAGhmf','TEST','n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=',1,'0677088','test',8),(93,NULL,'송경희','uS+/9L5MS36D3b6479NtnH5UDKol3SM0YF6gUEyUAv4=',1,'0623650','ssss',8),(125,NULL,'송챙이','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',1,'0666666','aaaa',12),(139,NULL,'김싸피','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'propri',12),(140,NULL,'김싸피','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'proprif',12),(156,NULL,'김','test3',1,'0005453','test3',6),(162,NULL,'김','test4',1,'0005453','test4',6),(165,NULL,'김','test5',0,NULL,'test5',6),(171,NULL,'송컨설','1234',0,NULL,'songcon',8),(174,NULL,'싸프로','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'sssppp',18),(177,'dgGk1biXRZ2a5t2ASLV-RP:APA91bHZPTKbMqhxDaF2mH_pASIHBMnpFJ1IDnaySscif9E4_YcCfu-Gkl7ZYTiVlqmnL7vvRGMs22G-nU9awp8yMFUEm_4k6QQS2xwMucZZuLsbcNl_X2m9f8MlX0tnTc_ymGJSE8U-','최지우','n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=',1,'0627088','jiwoo',8),(183,NULL,'이름','test',1,'0523','test9',8),(195,'eUGV2go7QFec9u_aASH5jW:APA91bHzgDVzfvVRQmL3uHtffL5nD3HBYOCsAz42Ceo9zo6PdA4PDBNdMMHZjRVlkJlResp7lrqZ0MqL3FSiO1Tc2jx0vqx5wnQpIXfvwjrBdWujWmNJju0rmki9C1oKclywTvNZoQMH','LEEBOYEON','n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=',1,'0628835','qhdus101',8),(355,NULL,'Admin','Kf4WIRuolMZY3Lg3kk6/F/sgGuuDB/vOdeKvMC736Lo=',0,NULL,'admin',8),(402,NULL,'최주은','gOWBFjx9pMjOl+9FL4StCdlmPJcjcu/g48X1dURHE5Q=',1,'0544867','jueun',8),(441,'df2LK5PJSCOqhYqbzoABbW:APA91bHvdJTTrcg4gKh6xS08EAX_fGzU7cw6ZJoyPPcDIkbrY8CEpO3kBQiXhzdNqa0axVatwmHuUUeS2XfItmRIvpBlVi5GZf05pcGm8e8gdNns0FHaMyycnwhBm0NcUDb6FsKfwsYk','최주은','TRpa2Xf0EbSDHT58BPjCRAoT/gtM75cXWyR34P1NfJY=',1,'0548622','jueun2',8),(507,'eXRPgCXGSSu8IUu_TsuUxE:APA91bHAQ3kmCOQOrP4xxDL1oMXfN3t4dngx5OOtjO-AJ8mx0O3VPssJlheYE091Mgov1dnAa0jViGtiZsFroObhdJPqnOSYnT_AwOWEz6VtYa4AMysrpiNOw83Wmcrgqd3x-uRW3urN','ADMIN','Kf4WIRuolMZY3Lg3kk6/F/sgGuuDB/vOdeKvMC736Lo=',0,NULL,'admin2',8),(532,NULL,'송경희','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'admin3',8),(549,NULL,'김미영','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'admin4',8),(572,NULL,'최지우','jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=',0,NULL,'jiwooAdmin',8),(657,NULL,'최주은','TRpa2Xf0EbSDHT58BPjCRAoT/gtM75cXWyR34P1NfJY=',1,'05432185','jueun3',8),(761,NULL,'TEST','0nxbLbfcOSoM/rGLl4LzRwnR3qe9uN1yCfbUxzh8eRA=',1,'12545212','test22',7),(764,NULL,'김민진','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'admin5',8),(768,NULL,'이태희','wRThIf2iVF4Pl1c82ZX0rhcs3aGVetg8A0WyFT89mAM=',0,NULL,'admin6',8),(1022,NULL,'교육생1','t6oZ0zrdk3uISgG0Vnp+IDjUbxeew9MXpmwsX5J80Gw=',1,'0123495','student',8),(1278,'','김싸피','t6oZ0zrdk3uISgG0Vnp+IDjUbxeew9MXpmwsX5J80Gw=',0,'0627088','ssafy',8);
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-18 10:56:12
