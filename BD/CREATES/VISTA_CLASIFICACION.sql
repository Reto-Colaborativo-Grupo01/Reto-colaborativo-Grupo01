DROP VIEW CLASIFICACION;

CREATE VIEW CLASIFICACION AS
SELECT 

    E.NOMBRE AS EQUIPO,
    COUNT(CASE WHEN P.GOLES_EQUIPO1 > P.GOLES_EQUIPO2 THEN 1 END) AS VICTORIAS,
    SUM(CASE WHEN E.ID = P.ID_EQUIPO1 THEN P.GOLES_EQUIPO1 ELSE P.GOLES_EQUIPO2 END) AS GOLES_A_FAVOR
FROM EQUIPOS E, PARTIDOS P
WHERE E.ID = P.ID_EQUIPO1 OR E.ID = P.ID_EQUIPO2
GROUP BY E.ID, E.NOMBRE, E.PRESUPUESTO_ANUAL
ORDER BY VICTORIAS DESC, GOLES_A_FAVOR DESC;


SELECT * FROM CLASIFICACION;