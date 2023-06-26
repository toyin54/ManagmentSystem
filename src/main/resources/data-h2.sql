--PAYMENT METHOD :

INSERT INTO STUDENTS (id,PASSWORD,USERNAME,ADDRESS,DOB,EMAIL,FIRST_NAME,LAST_NAME,PHONE_NUMBER,SSN)
VALUES(123,'123','123',12,'1999-05-15','Ds','ds','ds',123,123);
--insert into student(id) values (12);
--INSERT INTO payment_method (id,stu_id, payment_date, amount, method, trans_id, remarks)
--VALUES (12,123456, '2023-04-19', 100.0, 'credit_card', '1234567890', 'Payment for tuition fees1');

INSERT INTO payment_method (id,stu_id, payment_date, amount, method, trans_id, remarks)
VALUES (23,123, '2022-05-19', 1244.0, 'credit_car', '123455454567890', 'Payment for tuition fees2');

--INSERT INTO payment_method (id,stu_id, payment_date, amount, method, trans_id, remarks)
--VALUES (44,1236, '2022-04-19', 1244.0, 'credit_card', '123455454567890', 'Payment for tuition fees2');

--PAYMENT RECORDS :
INSERT INTO payment_record (course_id,course_fee) VALUES(123,3500);
INSERT INTO payment_record (course_id,course_fee) VALUES(153,4567);

--INSERT INTO payment_record (course_id,course_fee) VALUES(153,3760);