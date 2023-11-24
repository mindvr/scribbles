INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, deptno)
values (7788, 'SCOTT', 'ANALYST', 7566, NULL, 3000, 20);

INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, deptno)
values (7902, 'FORD', 'ANALYST', 7566, null, 3000, 20);

CREATE TABLE emp_new
(
    empno    INTEGER,
    ename    VARCHAR(10),
    job      VARCHAR(9),
    mgr      INTEGER,
    hiredate TEXT NOT NULL,
    sal      REAL,
    deptno   INTEGER,
    PRIMARY KEY (empno),
    FOREIGN KEY (deptno) REFERENCES dept (deptno)
);

INSERT INTO emp_new (empno, ename, job, mgr, hiredate, sal, deptno)
SELECT empno, ename, job, mgr, hiredate, sal, deptno FROM emp;

DROP TABLE emp;

ALTER TABLE emp_new RENAME TO emp;
