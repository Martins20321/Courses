alter table patient add active tinyint;
update patient set active = 1;