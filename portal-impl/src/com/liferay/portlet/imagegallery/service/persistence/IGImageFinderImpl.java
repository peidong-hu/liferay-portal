/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.QueryUtil;
import com.liferay.portal.kernel.dao.hibernate.SQLQuery;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.hibernate.Type;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.util.dao.hibernate.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="IGImageFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IGImageFinderImpl
	extends BasePersistenceImpl implements IGImageFinder {

	public static String COUNT_BY_FOLDER_IDS =
		IGImageFinder.class.getName() + ".countByFolderIds";

	public static String COUNT_BY_GROUP_ID =
		IGImageFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_G_U =
		IGImageFinder.class.getName() + ".countByG_U";

	public static String FIND_BY_GROUP_ID =
		IGImageFinder.class.getName() + ".findByGroupId";

	public static String FIND_BY_NO_ASSETS =
		IGImageFinder.class.getName() + ".findByNoAssets";

	public static String FIND_BY_UUID_G =
		IGImageFinder.class.getName() + ".findByUuid_G";

	public static String FIND_BY_G_U =
		IGImageFinder.class.getName() + ".findByG_U";

	public int countByFolderIds(List<Long> folderIds) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_FOLDER_IDS);

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]", getFolderIds(folderIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < folderIds.size(); i++) {
				Long folderId = folderIds.get(i);

				qPos.add(folderId);
			}

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_U(long groupId, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGImage> findByGroupId(long groupId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGImage> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGImage findByUuid_G(String uuid, long groupId)
		throws NoSuchImageException, SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_UUID_G);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(uuid);
			qPos.add(groupId);

			List<IGImage> list = q.list();

			if (list.size() == 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("No IGImage exists with the key {uuid=");
				sb.append(uuid);
				sb.append(", groupId=");
				sb.append(groupId);
				sb.append("}");

				throw new NoSuchImageException(sb.toString());
			}
			else {
				return list.get(0);
			}
		}
		catch (NoSuchImageException nsie) {
			throw nsie;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGImage> findByG_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("IGImage", IGImageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			return (List<IGImage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getFolderIds(List<Long> folderIds) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append("folderId = ? ");

			if ((i + 1) != folderIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}