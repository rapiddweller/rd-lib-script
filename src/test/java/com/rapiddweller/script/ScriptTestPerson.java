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

import com.rapiddweller.common.NullSafeComparator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple JavaBean for testing.<br/><br/>
 * Created: 01.11.2011 10:46:12
 *
 * @author Volker Bergmann
 * @since 0.7.3
 */
public class ScriptTestPerson {

  private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * The Name.
   */
  public String name;
  /**
   * The Birth date.
   */
  public Date birthDate;
  /**
   * The Score.
   */
  public int score;
  /**
   * The Registered.
   */
  public boolean registered;
  /**
   * The Rank.
   */
  public char rank;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Script test person.
   */
  public ScriptTestPerson() {
    this("anonymous", null, 0, false, 'C');
  }

  /**
   * Instantiates a new Script test person.
   *
   * @param name       the name
   * @param birthDate  the birth date
   * @param score      the score
   * @param registered the registered
   * @param rank       the rank
   */
  public ScriptTestPerson(String name, Date birthDate, int score, boolean registered, char rank) {
    this.name = name;
    this.birthDate = birthDate;
    this.score = score;
    this.registered = registered;
    this.rank = rank;
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets birth date.
   *
   * @return the birth date
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Sets birth date.
   *
   * @param birthDate the birth date
   */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Gets score.
   *
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets score.
   *
   * @param age the age
   */
  public void setScore(int age) {
    this.score = age;
  }

  /**
   * Is registered boolean.
   *
   * @return the boolean
   */
  public boolean isRegistered() {
    return registered;
  }

  /**
   * Sets registered.
   *
   * @param registered the registered
   */
  public void setRegistered(boolean registered) {
    this.registered = registered;
  }

  /**
   * Gets rank.
   *
   * @return the rank
   */
  public char getRank() {
    return rank;
  }

  /**
   * Sets rank.
   *
   * @param rank the rank
   */
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
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
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
