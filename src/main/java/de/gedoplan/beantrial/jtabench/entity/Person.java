package de.gedoplan.beantrial.jtabench.entity;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class Person
{
  /**
   * Id.
   */
  @Id
  private String id;

  /**
   * Name.
   */
  private String name;

  public Person(String name)
  {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }

  protected Person()
  {
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    Person other = (Person) obj;
    if (this.id == null)
    {
      if (other.id != null)
      {
        return false;
      }
    }
    else if (!this.id.equals(other.id))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "Person [id=" + this.id + ", name=" + this.name + "]";
  }

}
