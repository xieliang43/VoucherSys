/**
 * 
 */
package com.voucher.comparator;

import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Comparator;

import com.voucher.entity.Region;

/**
 * 
 */
public class RegionComparator implements Comparator<Region> {
	RuleBasedCollator collator;

	public RegionComparator() {
		collator = (RuleBasedCollator) Collator
				.getInstance(java.util.Locale.CHINA);
	}

	@Override
	public int compare(Region o1, Region o2) {
		String name1 = o1.getName();
		String name2 = o2.getName();
		CollationKey c1 = collator.getCollationKey(name1);
		CollationKey c2 = collator.getCollationKey(name2);
		return collator.compare(((CollationKey) c1).getSourceString(),
				((CollationKey) c2).getSourceString());
	}

}
