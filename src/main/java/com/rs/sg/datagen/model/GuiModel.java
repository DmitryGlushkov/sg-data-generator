package com.rs.sg.datagen.model;

import com.rs.sg.datagen.AppConfig;
import com.rs.sg.datagen.service.DataManager;
import com.rs.sg.datagen.utils.FileUtils;
import com.rs.sg.datagen.utils.InputStreamString;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

@ManagedBean
@ViewScoped
public class GuiModel {

    @ManagedProperty("#{dataManager}")
    private DataManager dataManager;

    @ManagedProperty("#{connectionProperties}")
    private ConnectionProperties connectionProperties;

    private Map<String, List<String>> columsCache = new HashMap<>();    // K:table name;    V:list of columns names

    private boolean isConnected = false;
    private List<String> tables = new ArrayList<>();
    private Table table = new Table();
    private List<Definition> definitions;
    private List<Definition> definitionsNotNull;

    @PostConstruct
    void init() {
        definitions = new ArrayList<>(Arrays.asList(
                Definition.STRING,
                Definition.INTEGER,
                Definition.DOUBLE,
                Definition.DATE,
                Definition.INDEX,
                Definition.SEQUENCE,
                Definition.QUERY,
                Definition.SKIP,
                Definition.LINK));
        definitionsNotNull = new ArrayList<>(definitions);
        definitionsNotNull.remove(Definition.SKIP);
    }

    public void onConnect() {
        tables.clear();
        try {
            dataManager.createConnection(connectionProperties);
            tables.addAll(dataManager.getTableNames());
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO, "Connected", connectionProperties.toString()));

    }

    public List<String> completeTable(String query) {
        final String queryLower = query.toLowerCase();
        return tables.stream().filter(t -> t.startsWith(queryLower)).sorted().collect(Collectors.toList());
    }

    public void requestTable(AjaxBehaviorEvent event) {
        try {
            if (table.getColumns() == null) {
                table.setColumns(new ArrayList<>());
            }
            table.getColumns().clear();
            table.getColumns().addAll(dataManager.getColumns(table.getName()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
    }

    public List<String> requestColumns(String tableName) {
        if (columsCache.containsKey(tableName)) {
            return columsCache.get(tableName);
        } else {
            List<String> result = new ArrayList<>();
            try {
                result.add("");
                result.addAll(dataManager.getColumns(tableName).stream().map(c -> c.getName()).collect(Collectors.toList()));
                columsCache.put(tableName, result);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
            }
            return result;
        }
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setConnectionProperties(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Table getTable() {
        return table;
    }

    public List<Definition> availableDefinitions(boolean nullable) {
        return nullable ? definitions : definitionsNotNull;
    }

    public StreamedContent getFile() {
        if (table.getName().isEmpty() || table.getColumns().size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", "empty configuration"));
            return null;
        }

        String fileExtension = "py";
        String content = FileUtils.print(connectionProperties, table, dataManager, FileUtils.Print.JSON);
        InputStream in = new InputStreamString(content);
        String fileName = String.format("gencfg.%s", fileExtension);

        return new DefaultStreamedContent(in, "text/plain", fileName);
    }

    public void upload(FileUploadEvent event) throws Exception {
        if (!isConnected) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", "Database connection needed"));
            return;
        }
        UploadedFile uploadedFile = event.getFile();
        String[] lines = new String(uploadedFile.getContents()).split("\n");
        List<Table> parsedTables = FileUtils.parse(lines, dataManager, AppConfig.currentPrint);
        if (parsedTables.size() > 0) {
            table = parsedTables.get(0);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN, "Warning", "Can not read the file " + event.getFile().getFileName()));
        }
        System.out.println(1);
    }
}
