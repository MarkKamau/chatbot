insert into employee (first_name, last_name) values ('Mark', 'Empl1');
insert into employee (first_name, last_name) values ('Dorothy', 'Empl2');

insert into client (customer_number, first_name, last_name) values ('11','Carol','Clt1');
insert into client (customer_number, first_name, last_name) values ('11','Jane','Clt2');
insert into client (customer_number, first_name, last_name) values ('11','Fiona','Clt3');

insert into template (name, value) values ('template1','Hi <Client.FirstName>, I hope you phone no is still the same. Talk to you soon. <Employee.FirstName>');
insert into template (name, value) values ('template2','Hi <Client.FirstName>, This is second message. I am trying to reach you. <Employee.FirstName>');
insert into template (name, value) values ('template3','Hi <Client.FirstName>, This is third message. I am trying to reach you. <Employee.FirstName>');