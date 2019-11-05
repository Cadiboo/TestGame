package io.github.cadiboo.testgame.mod;

import io.github.cadiboo.testgame.util.Distribution;
import io.github.cadiboo.testgame.util.Ordering;
import io.github.cadiboo.testgame.util.version.Version;
import io.github.cadiboo.testgame.util.version.VersionRange;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cadiboo
 */
class ModInfo {

	String modId;
	Version version;
	VersionRange gameVersionRange;
	Distribution distribution;
	String displayName;
	URL modHomepageUrl;
	URL issueTrackerURL;
	URL updateJSONURL;
	String authors;
	String credits;
	String description;
	Dependency[] dependencies;
	transient String[] loadBeforeModIds;
	transient String[] loadAfterModIds;

	@Override
	public int hashCode() {
		return modId.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ModInfo that = (ModInfo) o;
		return this.modId.equals(that.modId);
	}

	@Override
	public String toString() {
		return modId + "@" + version;
	}

	// TODO: convert to arrays
	public void computeBeforeAndAfterModIds() {
		List<String> before = new ArrayList<>();
		List<String> after = new ArrayList<>();
		for (final Dependency dependency : dependencies) {
			if (dependency.ordering == Ordering.BEFORE)
				before.add(dependency.modId);
			else if (dependency.ordering == Ordering.AFTER)
				after.add(dependency.modId);
		}
		this.loadBeforeModIds = before.toArray(new String[0]);
		this.loadAfterModIds = after.toArray(new String[0]);
	}

}
