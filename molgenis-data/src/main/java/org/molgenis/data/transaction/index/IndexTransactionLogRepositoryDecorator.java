package org.molgenis.data.transaction.index;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.molgenis.data.AggregateQuery;
import org.molgenis.data.AggregateResult;
import org.molgenis.data.Entity;
import org.molgenis.data.EntityListener;
import org.molgenis.data.EntityMetaData;
import org.molgenis.data.Fetch;
import org.molgenis.data.Query;
import org.molgenis.data.Repository;
import org.molgenis.data.RepositoryCapability;
import org.molgenis.data.transaction.index.IndexTransactionLogEntryMetaData.CudType;
import org.molgenis.data.transaction.index.IndexTransactionLogEntryMetaData.DataType;

public class IndexTransactionLogRepositoryDecorator implements Repository
{
	private final Repository decorated;
	private final IndexTransactionLogService indexTransactionLogService;

	public IndexTransactionLogRepositoryDecorator(Repository decorated,
			IndexTransactionLogService indexTransactionLogService)
	{
		this.decorated = requireNonNull(decorated);
		this.indexTransactionLogService = requireNonNull(indexTransactionLogService);
	}

	@Override
	public Iterator<Entity> iterator()
	{
		return decorated.iterator();
	}

	@Override
	public Stream<Entity> stream(Fetch fetch)
	{
		return decorated.stream(fetch);
	}

	@Override
	public void close() throws IOException
	{
		decorated.close();
	}

	@Override
	public Set<RepositoryCapability> getCapabilities()
	{
		return decorated.getCapabilities();
	}

	@Override
	public String getName()
	{
		return decorated.getName();
	}

	@Override
	public EntityMetaData getEntityMetaData()
	{
		return decorated.getEntityMetaData();
	}

	@Override
	public long count()
	{
		return decorated.count();
	}

	@Override
	public Query query()
	{
		return decorated.query();
	}

	@Override
	public long count(Query q)
	{
		return decorated.count(q);
	}

	@Override
	public Stream<Entity> findAll(Query q)
	{
		return decorated.findAll(q);
	}

	@Override
	public Entity findOne(Query q)
	{
		return decorated.findOne(q);
	}

	@Override
	public Entity findOne(Object id)
	{
		return decorated.findOne(id);
	}

	@Override
	public Entity findOne(Object id, Fetch fetch)
	{
		return decorated.findOne(id, fetch);
	}

	@Override
	public Stream<Entity> findAll(Stream<Object> ids)
	{
		return decorated.findAll(ids);
	}

	@Override
	public Stream<Entity> findAll(Stream<Object> ids, Fetch fetch)
	{
		return decorated.findAll(ids, fetch);
	}

	@Override
	public AggregateResult aggregate(AggregateQuery aggregateQuery)
	{
		return decorated.aggregate(aggregateQuery);
	}

	@Override
	public void update(Entity entity)
	{
		decorated.update(entity);
		indexTransactionLogService.log(getEntityMetaData(), CudType.UPDATE, DataType.DATA, entity.getIdValue()
				.toString());
	}

	@Override
	public void update(Stream<? extends Entity> entities)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.UPDATE, DataType.DATA, null);
		decorated.update(entities);
	}

	@Override
	public void delete(Entity entity)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.DELETE, DataType.DATA, entity.getIdValue()
				.toString());
		decorated.delete(entity);
	}

	@Override
	public void delete(Stream<? extends Entity> entities)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.DELETE, DataType.DATA, null);
		decorated.delete(entities);
	}

	@Override
	public void deleteById(Object id)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.DELETE, DataType.DATA, id.toString());
		decorated.deleteById(id);
	}

	@Override
	public void deleteById(Stream<Object> ids)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.DELETE, DataType.DATA, null);
		decorated.deleteById(ids);
	}

	@Override
	public void deleteAll()
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.DELETE, DataType.DATA, null);
		decorated.deleteAll();
	}

	@Override
	public void add(Entity entity)
	{
		decorated.add(entity);
		indexTransactionLogService.log(getEntityMetaData(), CudType.ADD, DataType.DATA, entity.getIdValue().toString());
	}

	@Override
	public Integer add(Stream<? extends Entity> entities)
	{
		indexTransactionLogService.log(getEntityMetaData(), CudType.UPDATE, DataType.DATA, null);
		return decorated.add(entities);
	}

	@Override
	public void flush()
	{
		decorated.flush();
	}

	@Override
	public void clearCache()
	{
		decorated.clearCache();
	}

	@Override
	public void create()
	{
		decorated.create();
	}

	@Override
	public void drop()
	{
		decorated.drop();
	}

	@Override
	public void rebuildIndex()
	{
		decorated.rebuildIndex();
	}

	@Override
	public void addEntityListener(EntityListener entityListener)
	{
		decorated.addEntityListener(entityListener);
	}

	@Override
	public void removeEntityListener(EntityListener entityListener)
	{
		decorated.removeEntityListener(entityListener);
	}
}
