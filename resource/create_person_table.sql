/* drop table */

DROP TABLE IF EXISTS person;

CREATE TABLE `person` (
  `id`                  int(11) unsigned NOT NULL auto_increment,
  `lastname`            varchar(64),
  `infix`               varchar(16),
  `initials`            varchar(8),
  `firstname`           varchar(64),
  `dateofbirth`         datetime,
  `nationality`         varchar(16),
  `sex`                 tinyint(1),
  `streetname`          varchar(64),
  `housenumber`         int(11) unsigned,
  `numberappendix`      varchar(8), 
  `city`                varchar(64),
  `zipcode`             varchar(8),
  `countrycode`         varchar(16),
  `telephonenumber`     varchar(16),
  `mobilenumber`        varchar(16),
  `emailaddress`        varchar(64), 
  PRIMARY KEY           (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
