package br.com.getmo.appsblocker;

import java.util.Comparator;

public class AppNameComparator implements Comparator {

	@Override
	public int compare( Object lhs, Object rhs ) {

		String name1 = ( ( AppInfo )lhs ).appName;
		String name2 = ( ( AppInfo )rhs ).appName;

		return name1.compareTo( name2 );
	}
}