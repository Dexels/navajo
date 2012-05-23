package com.dexels.navajo.mapping.compiler.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SQLFieldDependencyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuery1() {

		String example = "Call sp_processinsertperson( "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?)";

		SQLFieldDependency s = new SQLFieldDependency(-1, "SQLMap", "sql",
				example);

		assertTrue(s.hasMultipleDependencies());
		assertNotNull(s.getMultipleDependencies());
		assertEquals(1, s.getMultipleDependencies().length);
		assertEquals("sp_processinsertperson",
				((AdapterFieldDependency) s.getMultipleDependencies()[0])
						.getEvaluatedId());
		assertEquals("sp_processinsertperson",
				s.getMultipleDependencies()[0].getId());
		assertEquals("storedproc",
				((AdapterFieldDependency) s.getMultipleDependencies()[0])
						.getType());

	}

	@Test
	public void testQuery2() {

		String example = "SELECT "
				+ " (SELECT SUM(noot) FROM mies )"
				+ " , ( SELECT COUNT(*) "
				+ ",  (SELECT codedesc FROM vw_code_obj, (SELECT kokosnoot FROM\noerwoud\noer\nWHERE\ncodeid = vw_matches.status AND typeofcode = 'MATCH_STATUS' AND language = get_default_language()) AS statusdesc,"
				+ "FROM   organizationperson um "
				+ "WHERE  um.personid      = vw_person.personid "
				+ "AND    um.roleid        = 'UNIONMEMBER' "
				+ "AND    TRUNC( SYSDATE ) BETWEEN TRUNC( um.relationstart ) AND TRUNC( um.relationend ) "
				+ ") AS isunionmember "
				+ ",( SELECT COUNT(*) "
				+ "FROM   organizationpersonhistory hm "
				+ "WHERE  hm.personid = vw_person.personid "
				+ "AND    hm.roleid   = 'UNIONMEMBER' "
				+ ") AS isoldunionmember "
				+ " , CASE WHEN vmc.status = 'INVALIDATED' THEN 'INVALIDATED' "
				+ "WHEN vmc.status IN ('CANCELED','DRAFT') THEN 'CANCELED' "
				+ "WHEN vmc.value IN ('NEW', 'SCHEDULED', 'DRAFT') THEN 'INSERTED' "
				+ "WHEN ( ( SELECT COUNT(DISTINCT key) FROM changelogdetail v WHERE v.changelogid = vmc.changelogid AND v.processed = '0' AND v.key NOT LIKE 'NEW_%' ) > 1 AND vmc.status NOT IN ('INVALIDATED','CANCELED'))"
				+ "THEN 'MULTIPLE_CHANGES'" + "ELSE vmc.key " + "FROM\n"
				+ "vw_person, aap, vw_person, kibbeling\n" + "WHERE\n"
				+ "aap.id = vw_person.id";

		SQLFieldDependency s = new SQLFieldDependency(-1, "SQLMap", "sql",
				example);

		assertTrue(s.hasMultipleDependencies());
		assertNotNull(s.getMultipleDependencies());
		assertEquals(8, s.getMultipleDependencies().length);

		assertEquals("aap", s.getMultipleDependencies()[0].getId());
		assertEquals("changelogdetail", s.getMultipleDependencies()[1].getId());
		assertEquals("kibbeling", s.getMultipleDependencies()[2].getId());
		assertEquals("oerwoud", s.getMultipleDependencies()[3].getId());
		assertEquals("organizationpersonhistory",
				s.getMultipleDependencies()[4].getId());
		assertEquals("vw_person", s.getMultipleDependencies()[5].getId());
		assertEquals("vw_code_obj", s.getMultipleDependencies()[6].getId());
		assertEquals("mies", s.getMultipleDependencies()[7].getId());

	}

	@Test
	public void testQuery3() {
		String example = "SingleValueQuery( 'sportlinkkernel: SELECT COUNT(*) FROM vw_player WHERE personid = ? AND organizationid = ? AND sportid = ?',"
				+ "[/@PersonId], [/@OrganizationId], [/@SportId]) &gt; 0";

		SQLFieldDependency s = new SQLFieldDependency(-1, "SQLMap", "sql",
				example);
		assertNotNull(s.getMultipleDependencies());
		assertEquals(1, s.getMultipleDependencies().length);
		assertEquals("vw_player", s.getMultipleDependencies()[0].getId());
		assertEquals("table",
				((AdapterFieldDependency) s.getMultipleDependencies()[0])
						.getType());

	}
}
