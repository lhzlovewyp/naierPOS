/**
 * 
 */
package com.joker.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.TreeNode;
import com.joker.common.service.MaterialCategoryService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.TreeUtil;

/**
 * @author zhangfei
 * 
 */
@Controller
public class MaterialCategoryController extends AbstractController {

	@Autowired
	MaterialCategoryService materialCategoryService;

	/**
	 * 查询品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialCategoryByPage(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String likeName = (String) params.get("likeName");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			map.put("likeName", likeName);
			Page<MaterialCategory> page = materialCategoryService
					.getMaterialCategoryPageByCondition(map, pageNo, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 查询品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialCategoryByList(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<MaterialCategory> list = materialCategoryService
					.getMaterialCategoryPageByCondition(map);
			rbody.setData(list);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 查询品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialCategoryById(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			MaterialCategory materialCategory = materialCategoryService
					.getMaterialCategoryByID(id);
			rbody.setData(materialCategory);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 查询品类树信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/queryByTree" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getTree(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<MaterialCategory> materialCategorys = materialCategoryService
					.getMaterialCategoryPageByCondition(map);
			String treeJson = null;
			if (CollectionUtils.isNotEmpty(materialCategorys)) {
				List<TreeNode> treeNodes = new ArrayList<TreeNode>();
				for (MaterialCategory materialCategory : materialCategorys) {
					TreeNode treeNode = new TreeNode();
					treeNode.setId(materialCategory.getId());
					treeNode.setText(materialCategory.getName());
					if (materialCategory.getParent() != null) {
						treeNode.setParentId(materialCategory.getParent()
								.getId());
					}
					treeNodes.add(treeNode);
				}
				TreeNode rootTreeNode = TreeUtil.makeTree(treeNodes, "0");
				treeJson = JSONObject.toJSONString(rootTreeNode.getNodes());
			}
			rbody.setData(treeJson);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 添加品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String parentId = (String) params.get("parentId");
		String clientId = (String) params.get("clientId");

		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品类编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品类名称！");
			return rbody;
		}
		if (StringUtils.isBlank(clientId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商户！");
			return rbody;
		}
		if (StringUtils.isBlank(parentId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入上级品类！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Client client = new Client();
			client.setId(clientId);

			MaterialCategory addMaterialCategory = new MaterialCategory();
			addMaterialCategory.setId(UUID.randomUUID().toString());
			addMaterialCategory.setCode(code);
			addMaterialCategory.setName(name);
			addMaterialCategory.setClient(client);
			addMaterialCategory.setCreated(new Date());
			addMaterialCategory.setCreator(account.getId());

			MaterialCategory parentMaterialCategory = new MaterialCategory();
			parentMaterialCategory.setId(parentId);
			addMaterialCategory.setParent(parentMaterialCategory);

			materialCategoryService.insertMaterialCategory(addMaterialCategory);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String clientId = (String) params.get("clientId");
		String status = (String) params.get("status");
		String parentId = (String) params.get("parentId");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品类编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品类名称！");
			return rbody;
		}
		if (StringUtils.isBlank(clientId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商户！");
			return rbody;
		}
		if (StringUtils.isBlank(parentId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入上级品类！");
			return rbody;
		}
		if (StringUtils.isBlank(status)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入状态！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Client client = new Client();
			client.setId(clientId);

			MaterialCategory updateMaterialCategory = new MaterialCategory();
			updateMaterialCategory.setId(id);
			updateMaterialCategory.setCode(code);
			updateMaterialCategory.setName(name);
			updateMaterialCategory.setClient(client);
			updateMaterialCategory.setStatus(status);
			updateMaterialCategory.setModified(new Date());
			updateMaterialCategory.setEditor(account.getId());
			
			MaterialCategory parentMaterialCategory = new MaterialCategory();
			parentMaterialCategory.setId(parentId);
			updateMaterialCategory.setParent(parentMaterialCategory);

			materialCategoryService
					.updateMaterialCategory(updateMaterialCategory);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除品类信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/materialCategory/delete" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody delete(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请勾选需要删除的账号！");
			return rbody;
		}
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			materialCategoryService.deleteMaterialCategoryByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}