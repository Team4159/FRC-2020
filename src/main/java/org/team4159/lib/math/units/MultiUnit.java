package org.team4159.lib.math.units;

import java.util.*;

class MultiUnit extends Unit {
  private Map<Unit, Integer> units;

  MultiUnit(Map<Unit, Integer> units) {
    this.units = units;
  }

  @Override
  public Unit mult(Unit other) {
    Map<Unit, Integer> new_units = getUnits();
    if (other instanceof MultiUnit) {
      for (Map.Entry<Unit, Integer> unit_entry: ((MultiUnit) other).getUnits().entrySet()) {
        new_units.merge(unit_entry.getKey(), unit_entry.getValue(), Integer::sum);
        if (new_units.get(unit_entry.getKey()) == 0) new_units.remove(unit_entry.getKey());
      }
    } else {
      new_units.merge(other, 1, Integer::sum);
    }
    return new MultiUnit(new_units);
  }

  @Override
  public Unit inv() {
    HashMap<Unit, Integer> inverse_units = new HashMap<>();
    for (Map.Entry<Unit, Integer> unit_entry: getUnits().entrySet()) {
      units.put(unit_entry.getKey(), -unit_entry.getValue());
    }
    return new MultiUnit(inverse_units);
  }

  @Override
  public String symbol() {
    ArrayList<Map.Entry<Unit, Integer>> sorted_units = new ArrayList<>(getUnits().entrySet());
    sorted_units.sort((a, b) -> {
      int compare = a.getValue() - b.getValue();
      if (compare == 0) compare = b.getKey().symbol().compareTo(a.getKey().symbol());
      return compare;
    });
    StringBuilder name = new StringBuilder();
    boolean slashed = false;
    for (int i =  sorted_units.size() - 1; i >= 0; i--) {
      Map.Entry<Unit, Integer> unit_entry = sorted_units.get(i);
      if (!slashed && unit_entry.getValue() < 0) {
        if (i == sorted_units.size() - 1) name.append(1);
        name.append("/");
        slashed = true;
      }
      int order = Math.abs(unit_entry.getValue());
      if (order > 0) name.append(unit_entry.getKey().symbol());
      if (order > 1) name.append("^").append(order);
    }
    return name.toString();
  }

  public Map<Unit, Integer> getUnits() {
    return new HashMap<>(units);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MultiUnit)) return false;
    return units.equals(((MultiUnit) obj).units);
  }
}
