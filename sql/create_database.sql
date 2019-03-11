DROP TABLE IF EXISTS `plane_crashes`;
DROP TABLE IF EXISTS `incomes`;
DROP TABLE IF EXISTS `tsunamis`;
DROP TABLE IF EXISTS `storms`;
DROP TABLE IF EXISTS `population`;
DROP TABLE IF EXISTS `floods`;
DROP TABLE IF EXISTS `extreme_temperatures`;
DROP TABLE IF EXISTS `epidemics`;
DROP TABLE IF EXISTS `earthquakes`;
DROP TABLE IF EXISTS `droughts`;
DROP TABLE IF EXISTS `countries`;
DROP TABLE IF EXISTS `years`;

# Dump of table countries
# ------------------------------------------------------------
CREATE TABLE `countries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `countryName_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table years
# ------------------------------------------------------------

CREATE TABLE `years` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table droughts
# ------------------------------------------------------------

CREATE TABLE `droughts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `droughts_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `droughts_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table earthquakes
# ------------------------------------------------------------


CREATE TABLE `earthquakes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `earthquakes_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `earthquakes_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table epidemics
# ------------------------------------------------------------


CREATE TABLE `epidemics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `epidemics_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `epidemics_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table extreme_temperatures
# ------------------------------------------------------------

CREATE TABLE `extreme_temperatures` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `extreme_temperatures_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `extreme_temperatures_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table floods
# ------------------------------------------------------------

CREATE TABLE `floods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `floods_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `floods_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table incomes
# ------------------------------------------------------------

CREATE TABLE `incomes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `income` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `incomes_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `incomes_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table plane_crashes
# ------------------------------------------------------------

CREATE TABLE `plane_crashes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `plane_crashes_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `plane_crashes_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table population
# ------------------------------------------------------------

CREATE TABLE `population` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `population` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `year_id` (`year_id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `population_ibfk_1` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `population_ibfk_2` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table storms
# ------------------------------------------------------------

CREATE TABLE `storms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `storms_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storms_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table tsunamis
# ------------------------------------------------------------

CREATE TABLE `tsunamis` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` int(11) unsigned NOT NULL,
  `year_id` int(11) unsigned NOT NULL,
  `affected` int(10) unsigned NOT NULL,
  `deaths` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  KEY `year_id` (`year_id`),
  CONSTRAINT `tsunamis_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tsunamis_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


