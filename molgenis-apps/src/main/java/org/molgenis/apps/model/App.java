package org.molgenis.apps.model;

import org.molgenis.data.Entity;
import org.molgenis.data.meta.model.EntityMetaData;
import org.molgenis.data.support.StaticEntity;
import org.molgenis.file.model.FileMeta;

import static org.molgenis.apps.model.AppMetaData.*;

public class App extends StaticEntity
{
	public App(Entity entity)
	{
		super(entity);
	}

	public App(EntityMetaData entityMeta)
	{
		super(entityMeta);
	}

	public App(String name, EntityMetaData entityMeta)
	{
		super(entityMeta);
		setAppName(name);
	}

	public String getAppName()
	{
		return getString(APP_NAME);
	}

	public void setAppName(String name)
	{
		set(APP_NAME, name);
	}

	public FileMeta getSourceFiles()
	{
		return getEntity(RESOURCE_FILES, FileMeta.class);
	}

	public void setSourceFiles(String sourcesDirectory)
	{
		set(RESOURCE_FILES, sourcesDirectory);
	}

	public boolean isActive()
	{
		return getBoolean(ACTIVE);
	}

	public void setActive(boolean isActive)
	{
		set(ACTIVE, isActive);
	}
}
