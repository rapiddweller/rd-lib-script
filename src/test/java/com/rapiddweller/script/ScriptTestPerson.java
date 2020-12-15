/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.script;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rapiddweller.commons.NullSafeComparator;

/**
 * Simple JavaBean for testing.<br/><br/>
 * Created: 01.11.2011 10:46:12
 * @since 0.7.3
 * @author Volker Bergmann
 */
public class ScriptTestPerson {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public String name;
	public Date birthDate;
	public int score;
	public boolean registered;
	public char rank;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public ScriptTestPerson() {
		this("anonymous", null, 0, false, 'C');
	}

	public ScriptTestPerson(String name, Date birthDate, int score, boolean registered, char rank) {
		this.name = name;
		this.birthDate = birthDate;
		this.score = score;
		this.registered = registered;
		this.rank = rank;
	}
	
	// properties ------------------------------------------------------------------------------------------------------

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int age) {
		this.score = age;
	}
	
	public boolean isRegistered() {
		return registered;
	}
	
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	public char getRank() {
		return rank;
	}
	
	public void setRank(char rank) {
		this.rank = rank;
	}
	
	// java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rank;
		result = prime * result + (registered ? 1231 : 1237);
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScriptTestPerson that = (ScriptTestPerson) obj;
		return NullSafeComparator.equals(this.birthDate, that.birthDate) 
			&& NullSafeComparator.equals(this.name, that.name)
			&& this.rank == that.rank
			&& this.registered == that.registered
			&& this.score == that.score;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name='" + name + "', " +
				"birthDate=" + (birthDate != null ? df.format(birthDate) : "null") + ", " +
				"score=" + score + ", registered=" + registered + ", rank='" + rank + "'";
	}
	
}
