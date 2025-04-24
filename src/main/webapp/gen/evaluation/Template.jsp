<%@page language="java" pageEncoding="UTF-8" import="com.iassets.general.entity.*,com.iassets.general.bo.*,java.util.*,com.iassets.common.util.*" %>
<%
String msg = WebUtil.getSessionMessage(request);
EvaluationTemplate template = (EvaluationTemplate) request.getAttribute("evaluationTemplate"); 
List <EvaluationTemplateItem> items = null;
String groupId = "";
String groupName = "";

if (template != null) {
	items = template.getItemsList();
	GenLookupEvaluationGroup group = template.getGroup();
	if(group != null){
	   groupId = "" + group.getId();
	   groupName = group.getName();
	}
}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<form id="form-<%=groupId%>" name="form-<%=groupId%>" target="iframe-<%=groupId%>" method="post" action="gen/EvaluationProcess">

  <input type="hidden" name="groupId" id="groupId" value="<%=groupId%>">
  
  <%=msg%>
  <h1>جدول الغرامات الخاصة بمستوى الأداء لقسم <%=groupName%></h1>
  
  <table id="items_table_<%=groupId%>" class="data_table">
    <thead>
	    <tr>
	      <th>البيان</th>
	      <th>الدرجة القصوي</th>
	      <th>درجة تقدير الأداء</th>
	      <th>نسبة الأداء %</th>
	      <th>نسبة الحسم %</th>
	      <th>المبلغ للنشاط الواحد</th>
	      <th>قيمة الغرامة</th>
	    </tr>
    </thead>
	<tbody>
    <% 
    int counter = 0;
    int itemMaxDegreeTotal = 0;
    float itemEvalDegreeTotal = 0;
    //float itemEvalPercentageTotal = 0;
    //float itemPenaltyPercentageTotal = 0;
    float itemDedicatedValueTotal = 0;
    float itemPenaltyValueTotal = 0;
    String htmlElemNameSuffix = null;
    if (items != null){
      for (EvaluationTemplateItem item : items){
    	  counter = counter + 1;
    	  htmlElemNameSuffix = groupId + "_" + counter;
    	  itemMaxDegreeTotal += item.getMaxDegree();
    	  itemEvalDegreeTotal += item.getEvalDegree();
    	  //itemEvalPercentageTotal += item.getEvalPercentage();
    	  //itemPenaltyPercentageTotal += item.getPenaltyPercentage();
    	  itemDedicatedValueTotal += item.getCurrentDedicatedValue();
    	  itemPenaltyValueTotal += item.getPenaltyValue();
      %>
	    <tr>
	      <td align="right">
		      <%=counter%> - <%=item.getItem().getName()%>
		      <input name="itemId_<%=htmlElemNameSuffix%>" type="hidden" value="<%=item.getItem().getId()%>">
	      </td>
	      <td align="center"><input readonly="readonly" class="view_mode" name="itemMaxDegree_<%=htmlElemNameSuffix%>" type="text" value="<%=item.getMaxDegree()%>" size="7"></td>
	      <td align="center"><input required data-msg-required="مطلوب" data-rule-number="true" data-msg-number="أدخل رقم" data-rule-min="0" data-msg-min="قيمة مرفوضة" data-rule-max="<%=item.getMaxDegree()%>" data-msg-max="قيمة مرفوضة" tabindex="<%=counter%>" id="itemEvalDegree_<%=htmlElemNameSuffix%>" name="itemEvalDegree_<%=htmlElemNameSuffix%>" type="text" value="<%=item.getEvalDegree()%>" onblur="updateCalculatedValues(this,'<%=htmlElemNameSuffix%>','<%=groupId%>')"  size="7"></td>
	      <td align="center"><input readonly="readonly" class="view_mode" name="itemEvalPercentage_<%=htmlElemNameSuffix%>" type="text" value="<%=item.getEvalPercentage()%>" size="7"></td>
	      <td align="center"><input readonly="readonly" class="view_mode" name="itemPenaltyPercentage_<%=htmlElemNameSuffix%>" type="text" value="<%=item.getPenaltyPercentage()%>" size="7"></td>
	      <td align="center"><input readonly="readonly" class="view_mode" name="itemDedicatedValue_<%=htmlElemNameSuffix%>" type="text" value="<%=Common.formatMoneyValue(item.getCurrentDedicatedValue())%>" size="7"></td>
	      <td align="center"><input readonly="readonly" class="view_mode" name="itemPenaltyValue_<%=htmlElemNameSuffix%>" type="text" value="<%=Common.formatMoneyValue(item.getPenaltyValue())%>" size="7"></td>
	    </tr>
    <%}}%>
    </tbody>
    <tfoot>
	    <tr>
	      <th>المجموع   
	      <input type="hidden" name="totalItemsCount" id="totalItemsCount_<%=groupId%>" value="<%=counter%>"></th>
	      <th id="itemMaxDegree_<%=groupId%>" ><%=itemMaxDegreeTotal%></th>
	      <th id="itemEvalDegree_<%=groupId%>" ><%=itemEvalDegreeTotal %></th>
	      <th id="itemEvalPercentage_<%=groupId%>" >&nbsp;<%-- <%=itemEvalPercentageTotal %> --%></th>
	      <th id="itemPenaltyPercentage_<%=groupId%>" >&nbsp;<%-- <%=itemPenaltyPercentageTotal %> --%></th>
	      <th id="itemDedicatedValue_<%=groupId%>" ><%= Common.formatMoneyValue(itemDedicatedValueTotal) %></th>
	      <th id="itemPenaltyValue_<%=groupId%>" ><%=Common.formatMoneyValue(itemPenaltyValueTotal)%></th>
	    </tr>
	    <tr>
	      <th>المبلغ المستحق</th>    
	      <th colspan="6" class="sheet_due_<%=groupId%>"></th>
	    </tr>
    </tfoot>
  </table>
  <div>
    <input onclick="return _submitPerformanceEvalForm('<%=groupId%>')" type="submit" name="submit" class="tab_submit" value="تطبيق التعديلات">
  </div>
</form>
</body>
</html>