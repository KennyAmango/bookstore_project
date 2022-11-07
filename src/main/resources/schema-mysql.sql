CREATE TABLE IF NOT EXISTS`bookstore1` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `storage` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `writer` varchar(20) DEFAULT NULL,
  `sales_volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;