package io.github.cadiboo.testgame.mod;

import io.github.cadiboo.testgame.util.Distribution;
import io.github.cadiboo.testgame.util.Ordering;
import io.github.cadiboo.testgame.util.version.VersionRange;

import java.net.URL;

/**
 * @author Cadiboo
 */
class Dependency {

	String modId;
	VersionRange versionRange;
	boolean required;
	URL modHomepageUrl;
	Ordering ordering;
	Distribution distribution;

}
