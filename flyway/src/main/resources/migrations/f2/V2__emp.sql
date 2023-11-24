CREATE TABLE emp
(
    empno    INTEGER,
    ename    VARCHAR(10),
    job      VARCHAR(9),
    mgr      INTEGER,
    hiredate TEXT,
    sal      REAL,
    deptno   INTEGER,
    PRIMARY KEY (empno),
    FOREIGN KEY (deptno) REFERENCES dept (deptno)
);

INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, deptno)
VALUES (7839, 'KING', 'PRESIDENT', NULL, '1981-11-17', 5000, 10);

INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, deptno)
VALUES (7698, 'BLAKE', 'MANAGER', 7839, '1981-05-01', 2850, 30);

INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, deptno)
values (7566, 'JONES', 'MANAGER', 7839, '1981-02-04', 2975, 20);