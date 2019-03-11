LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/years.txt' INTO TABLE years
	FIELDS TERMINATED BY ''
	LINES TERMINATED BY '\n'
	(years.`year`)
	SET id=NULL;

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/countries.txt'
	INTO TABLE countries (countries.`name`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/incomes.txt'
	INTO TABLE incomes
	FIELDS TERMINATED BY ','
	(incomes.`country_id`, incomes.`year_id`, incomes.`income`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/populations.txt'
	INTO TABLE population
	FIELDS TERMINATED BY ','
	(population.`country_id`, population.`year_id`, population.`population`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/tsunamis.txt'
	INTO TABLE tsunamis
	FIELDS TERMINATED BY ','
	(tsunamis.`country_id`, tsunamis.`year_id`, tsunamis.`affected`, tsunamis.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/storms.txt'
	INTO TABLE storms
	FIELDS TERMINATED BY ','
	(storms.`country_id`, storms.`year_id`, storms.`affected`, storms.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/plane_crashes.txt'
	INTO TABLE plane_crashes
	FIELDS TERMINATED BY ','
	(plane_crashes.`country_id`, plane_crashes.`year_id`, plane_crashes.`affected`, plane_crashes.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/floods.txt'
	INTO TABLE floods
	FIELDS TERMINATED BY ','
	(floods.`country_id`, floods.`year_id`, floods.`affected`, floods.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/extreme_temperatures.txt'
	INTO TABLE extreme_temperatures
	FIELDS TERMINATED BY ','
	(extreme_temperatures.`country_id`, extreme_temperatures.`year_id`, extreme_temperatures.`affected`, extreme_temperatures.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/epidemics.txt'
	INTO TABLE epidemics
	FIELDS TERMINATED BY ','
	(epidemics.`country_id`, epidemics.`year_id`, epidemics.`affected`, epidemics.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/earthquakes.txt'
	INTO TABLE earthquakes
	FIELDS TERMINATED BY ','
	(earthquakes.`country_id`, earthquakes.`year_id`, earthquakes.`affected`, earthquakes.`deaths`);

LOAD DATA LOCAL INFILE '/Users/saen/Desktop/Spring-Semester-2019/Advanced-Topics-of-Database-Technology-and-Applications/Project/resources/droughts.txt'
	INTO TABLE droughts
	FIELDS TERMINATED BY ','
	(droughts.`country_id`, droughts.`year_id`, droughts.`affected`, droughts.`deaths`);